import { Component } from '@angular/core';
import { Transaction } from '../model/transaction';
import { TransactionService } from '../services/transaction.service';
import { CurrencyService } from '../services/currency.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppMaterialModule } from '../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { Account } from '../model/account';
import { AccountService } from '../services/account.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [AppMaterialModule, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css',
})
export class TransactionsComponent {
  public transactions: Transaction[] = [];
  public filteredTransactions: Transaction[] = [];
  public accounts: Account[] = [];
  public defaultCurrency: string = '';

  constructor(
    private transactionService: TransactionService,
    private currencyService: CurrencyService,
    private accountService: AccountService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.loadTransactions();
    this.loadAccounts();
    this.loadDefaultCurrency();
  }

  private loadDefaultCurrency() {
    this.currencyService
      .getDefaultCurrency()
      .subscribe((data: string) => (this.defaultCurrency = data));
  }

  private loadAccounts() {
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

  private loadTransactions() {
    this.transactions = [];
    this.transactionService.getTransactions().subscribe(
      (data: Transaction[]) => {
        if (data.length > 0) {
          this.transactions = this.filteredTransactions = data.map(
            (transaction: any) => {
              return new Transaction(
                transaction.transactionId,
                transaction.description,
                transaction.amount,
                transaction.defaultCurrencyAmount,
                transaction.account
              );
            }
          );
        }
      },
      (error: any) => {
        console.error('Error consuming transactions:', error);
      }
    );
  }

  public openAccountDialog() {}

  onOptionSelected(accountId: number): void {
    this.filteredTransactions = this.transactions.filter(
      (transaction) => transaction.account.accountId === accountId
    );
  }
}
