import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CurrencyResponse } from '../model/currency-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CurrencyService {
  private apiUrl = 'http://localhost:8080/currencies';

  constructor(private httpClient: HttpClient) {}

  getCurrencies(): Observable<CurrencyResponse[]> {
    return this.httpClient.get<CurrencyResponse[]>(`${this.apiUrl}`);
  }

  getDefaultCurrency(): Observable<string> {
    return this.httpClient.get(`${this.apiUrl}/default`, {
      responseType: 'text',
    });
  }

  updateDefaultCurrency(defaultCurrency: string) {
    return this.httpClient.put(`${this.apiUrl}/update`, defaultCurrency);
  }
}
