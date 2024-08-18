import { Component } from '@angular/core';
import { Account } from '../model/account';
import { AccountService } from '../services/account.service';
import { CurrencyService } from '../services/currency.service';
import { AppMaterialModule } from '../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { NewAccountDialogComponent } from './new-account-dialog/new-account-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [AppMaterialModule, CommonModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css',
})
export class AccountsComponent {
  public accounts: Account[] = [];
  public defaultCurrency: string = '';

  constructor(
    private accountService: AccountService,
    private currencyService: CurrencyService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.loadAccounts();
    this.loadDefaultCurrency();
  }

  private loadDefaultCurrency() {
    this.currencyService
      .getDefaultCurrency()
      .subscribe((data: string) => (this.defaultCurrency = data));
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
        }
      },
      (error: any) => {
        console.error('Error consuming accounts:', error);
      }
    );
  }

  public openAccountDialog() {
    const dialogRef = this.dialog.open(NewAccountDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      this.snackBar.open(
        'New account ' + (result ? 'was' : "wasn't") + ' added successfuly!',
        '',
        { duration: 3000 }
      );
      if (result) this.loadAccounts();
    });
  }
}
