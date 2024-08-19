import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CurrencyResponse } from '../model/currency-response';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CurrencyService {
  private apiUrl = 'http://localhost:8080/currencies';
  private CURRENCIES_KEY = 'currencies';
  private DEFAULT_CURRENCY_KEY = 'default_currency';

  constructor(private httpClient: HttpClient) {
    this.loadCurrencies();
    this.loadDefaultCurrency();
  }

  loadCurrencies(): void {
    this.httpClient
      .get<CurrencyResponse[]>(`${this.apiUrl}`)
      .subscribe((data: CurrencyResponse[]) => {
        let currencies = data.map((currency) => {
          return new CurrencyResponse(currency.id, currency.name);
        });
        localStorage.setItem(this.CURRENCIES_KEY, JSON.stringify(currencies));
      });
    console.log('ucitavanje');
  }

  getCurrencies(): CurrencyResponse[] {
    return JSON.parse(localStorage.getItem(this.CURRENCIES_KEY)!) || [];
  }

  loadDefaultCurrency(): void {
    this.httpClient
      .get(`${this.apiUrl}/default`, {
        responseType: 'text',
      })
      .subscribe((currency) => {
        localStorage.setItem(this.DEFAULT_CURRENCY_KEY, currency);
      });
  }

  getDefaultCurrency(): string {
    return localStorage.getItem(this.DEFAULT_CURRENCY_KEY) || '';
  }

  updateDefaultCurrency(defaultCurrency: string) {
    return this.httpClient.put(`${this.apiUrl}/update`, defaultCurrency).pipe(
      tap(() => {
        localStorage.setItem(this.DEFAULT_CURRENCY_KEY, defaultCurrency);
      })
    );
  }
}
