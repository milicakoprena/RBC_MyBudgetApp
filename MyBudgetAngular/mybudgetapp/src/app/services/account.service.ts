import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../model/account';
import { Observable, Subject } from 'rxjs';
import { AccountRequest } from '../model/account-request';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts';

  private refreshAccountsSubject = new Subject<void>();
  refreshAccounts$ = this.refreshAccountsSubject.asObservable();

  private refreshBalanceSource = new Subject<void>();
  refreshBalance$ = this.refreshBalanceSource.asObservable();

  constructor(private httpClient: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    return this.httpClient.get<Account[]>(`${this.apiUrl}/default`);
  }

  getAccountBalanceSum(): Observable<number> {
    return this.httpClient.get<number>(`${this.apiUrl}/sum`);
  }

  addAccount(accountRequest: AccountRequest) {
    return this.httpClient.post(this.apiUrl, accountRequest);
  }

  deleteAllAccounts() {
    return this.httpClient.delete(this.apiUrl);
  }

  notifyRefreshAccounts() {
    this.refreshAccountsSubject.next();
  }

  notifyRefreshBalance() {
    this.refreshBalanceSource.next();
  }
}
