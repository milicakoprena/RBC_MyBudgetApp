import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { CurrencyService } from '../../services/currency.service';
import { CurrencyResponse } from '../../model/currency-response';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Observable, map, startWith } from 'rxjs';
import { CommonModule } from '@angular/common';
import { AccountRequest } from '../../model/account-request';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-new-account-dialog',
  standalone: true,
  imports: [AppMaterialModule, FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-account-dialog.component.html',
  styleUrl: './new-account-dialog.component.css',
})
export class NewAccountDialogComponent implements OnInit {
  public currencies: CurrencyResponse[] = [];
  public myControl = new FormControl<CurrencyResponse | null>(null);
  public filteredCurrencies: Observable<CurrencyResponse[]> = new Observable<
    CurrencyResponse[]
  >();
  public defaultCurrency: string = '';
  public form: FormGroup;
  constructor(
    public dialogRef: MatDialogRef<NewAccountDialogComponent>,
    private currencyService: CurrencyService,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {
    this.form = this.fb.group({
      name: [null, Validators.required],
      currency: [null, Validators.required],
      balance: [0, Validators.required],
    });
  }
  ngOnInit(): void {
    this.loadDefaultCurrency();
    this.loadCurrencies();
  }

  private loadDefaultCurrency() {
    this.currencyService.getDefaultCurrency().subscribe((data: string) => {
      this.defaultCurrency = data;
    });
  }

  private loadCurrencies() {
    this.currencyService.getCurrencies().subscribe(
      (data: CurrencyResponse[]) => {
        this.currencies = data.map((currency) => {
          if (currency.id.toLowerCase() === this.defaultCurrency.toLowerCase())
            this.myControl.setValue(
              new CurrencyResponse(currency.id.toUpperCase(), currency.name)
            );
          return new CurrencyResponse(currency.id.toUpperCase(), currency.name);
        });

        this.filteredCurrencies = this.myControl.valueChanges.pipe(
          startWith(''),
          map((value) => {
            const id = typeof value === 'string' ? value : value?.id;
            return id ? this._filter(id as string) : this.currencies.slice();
          })
        );
      },
      (error: any) => {
        console.error('Error consuming currencies:', error);
      }
    );
  }

  displayFn(currency: CurrencyResponse): string {
    return currency && currency.id ? currency.id : '';
  }

  private _filter(id: string): CurrencyResponse[] {
    const filterValue = id.toLowerCase();
    return this.currencies.filter((currency) =>
      currency.id.toLowerCase().includes(filterValue)
    );
  }

  public submitForm() {
    const accountRequest: AccountRequest = {
      name: this.form.value.name,
      currency: this.myControl.getRawValue()?.id!,
      balance: this.form.value.balance,
    };
    this.accountService.addAccount(accountRequest).subscribe(
      (response) => {
        this.dialogRef.close(true);
      },
      (error) => {
        console.log('Error adding account', error);
        this.dialogRef.close(false);
      }
    );
  }
}
