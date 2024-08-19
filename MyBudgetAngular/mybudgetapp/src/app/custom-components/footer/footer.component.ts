import { Component, EventEmitter, Output } from '@angular/core';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/account.service';
import { CurrencyService } from '../../services/currency.service';
import { MatDialog } from '@angular/material/dialog';
import { NewTransactionDialogComponent } from '../../transactions/new-transaction-dialog/new-transaction-dialog.component';
import { TransactionService } from '../../services/transaction.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [AppMaterialModule, CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css',
})
export class FooterComponent {
  public totalBalance: number = 0;
  public defaultCurrency: string = '';

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService,
    private currencyService: CurrencyService,
    private dialog: MatDialog
  ) {
    this.loadBalanceSum();
    this.loadDefaultCurrency();
  }

  private loadBalanceSum() {
    this.accountService
      .getAccountBalanceSum()
      .subscribe((data: number) => (this.totalBalance = data));
  }

  private loadDefaultCurrency() {
    this.defaultCurrency = this.currencyService.getDefaultCurrency();
  }

  public openTransactionDialog() {
    const dialogRef = this.dialog.open(NewTransactionDialogComponent, {
      data: { defaultCurrency: this.defaultCurrency },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.transactionService.notifyRefreshTransactions();
      }
    });
  }
}
