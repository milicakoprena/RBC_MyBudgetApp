import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Transaction } from '../model/transaction';
import { TransactionRequest } from '../model/transaction-request';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private apiUrl = 'http://localhost:8080/transactions';
  private refreshTransactionsSubject = new Subject<void>();

  refreshTransactions$ = this.refreshTransactionsSubject.asObservable();

  constructor(private httpClient: HttpClient) {}

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(`${this.apiUrl}/default`);
  }

  addTransaction(transactionRequest: TransactionRequest) {
    return this.httpClient.post(this.apiUrl, transactionRequest);
  }

  notifyRefreshTransactions() {
    this.refreshTransactionsSubject.next();
  }
}
