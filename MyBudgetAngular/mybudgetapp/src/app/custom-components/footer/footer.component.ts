import { Component } from '@angular/core';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/account.service';
import { CurrencyService } from '../../services/currency.service';
import { MatDialog } from '@angular/material/dialog';
import { NewTransactionDialogComponent } from '../../transactions/new-transaction-dialog/new-transaction-dialog.component';

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
    this.currencyService
      .getDefaultCurrency()
      .subscribe((data: string) => (this.defaultCurrency = data));
  }

  public openTransactionDialog() {
    this.dialog.open(NewTransactionDialogComponent, {
      data: { defaultCurrency: this.defaultCurrency },
    });
  }
}
