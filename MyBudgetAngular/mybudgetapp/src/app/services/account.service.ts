import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../model/account';
import { Observable } from 'rxjs';
import { AccountRequest } from '../model/account-request';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts';

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
}
