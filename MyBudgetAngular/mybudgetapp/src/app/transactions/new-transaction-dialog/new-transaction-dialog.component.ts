import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TransactionService } from '../../services/transaction.service';
import { AccountService } from '../../services/account.service';
import { Account } from '../../model/account';
import { TransactionRequest } from '../../model/transaction-request';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-new-transaction-dialog',
  standalone: true,
  imports: [AppMaterialModule, FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-transaction-dialog.component.html',
  styleUrl: './new-transaction-dialog.component.css',
})
export class NewTransactionDialogComponent {
  public form: FormGroup;
  public accounts: Account[] = [];

  constructor(
    public dialogRef: MatDialogRef<NewTransactionDialogComponent>,
    private fb: FormBuilder,
    private transactionService: TransactionService,
    private accountService: AccountService,
    private snackBar: MatSnackBar,
    private cdr: ChangeDetectorRef,
    @Inject(MAT_DIALOG_DATA) public data: { defaultCurrency: string }
  ) {
    this.form = this.fb.group({
      description: [null, Validators.required],
      type: [null, Validators.required],
      accountId: [null, Validators.required],
      amount: [null, Validators.required],
    });
  }
  ngOnInit(): void {
    this.loadAccounts();
  }

  private loadAccounts() {
    this.accounts = [];
    this.accountService.getAccounts().subscribe(
      (data: Account[]) => {
        if (data.length > 0) {
          this.accounts = data.map((account: any) => {
            return new Account(
              account.accountId,
              account.name,
              account.currency,
              account.balance,
              account.defaultCurrencyBalance
            );
          });
          this.cdr.detectChanges();
        }
      },
      (error: any) => {
        console.error('Error consuming accounts:', error);
      }
    );
  }

  public submitForm() {
    const amount =
      this.form.value.type === 'expense'
        ? this.form.value.amount * -1
        : this.form.value.amount;
    const transactionRequest: TransactionRequest = {
      description: this.form.value.description,
      amount: amount,
      accountId: this.form.value.accountId,
    };
    this.transactionService.addTransaction(transactionRequest).subscribe(
      (response) => {
        this.snackBar.open('Transaction added!', '', { duration: 3000 });
        this.dialogRef.close(true);
      },
      (error) => {
        console.log('Error adding transaction', error);
        if (error.status === 402)
          this.snackBar.open(
            'Transaction failed because od insufficient funds on your account!',
            '',
            { duration: 3000 }
          );
        this.dialogRef.close(false);
      }
    );
  }
}
