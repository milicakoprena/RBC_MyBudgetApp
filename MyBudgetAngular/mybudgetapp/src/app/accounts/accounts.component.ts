import { Component, OnInit } from '@angular/core';
import { Account } from '../model/account';
import { AccountService } from '../services/account.service';
import { CurrencyService } from '../services/currency.service';
import { AppMaterialModule } from '../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { NewAccountDialogComponent } from './new-account-dialog/new-account-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [AppMaterialModule, CommonModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css',
})
export class AccountsComponent implements OnInit {
  public accounts: Account[] = [];
  public defaultCurrency: string = '';
  private subscription: Subscription = new Subscription();

  constructor(
    private accountService: AccountService,
    private currencyService: CurrencyService,
    private dialog: MatDialog
  ) {
    this.loadAccounts();
    this.loadDefaultCurrency();
  }

  ngOnInit(): void {
    this.subscription = this.accountService.refreshAccounts$.subscribe(() => {
      this.loadAccounts();
    });
  }
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private loadDefaultCurrency() {
    this.defaultCurrency = this.currencyService.getDefaultCurrency();
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
      if (result) {
        this.loadAccounts();
        this.accountService.notifyRefreshBalance();
      }
    });
  }
}
