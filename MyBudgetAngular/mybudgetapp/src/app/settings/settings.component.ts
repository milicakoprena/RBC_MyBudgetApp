import { Component, OnInit } from '@angular/core';
import { AppMaterialModule } from '../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { CurrencyResponse } from '../model/currency-response';
import { CurrencyService } from '../services/currency.service';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable, map, startWith } from 'rxjs';
import { CurrencyDialogComponent } from './currency-dialog/currency-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [AppMaterialModule, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
})
export class SettingsComponent implements OnInit {
  public currencies: CurrencyResponse[] = [];
  public myControl = new FormControl<CurrencyResponse | null>(null);
  public filteredCurrencies: Observable<CurrencyResponse[]> = new Observable<
    CurrencyResponse[]
  >();
  public defaultCurrency: string = '';

  constructor(
    private currencyService: CurrencyService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadDefaultCurrency();
    this.loadCurrencies();
  }

  private loadDefaultCurrency() {
    this.defaultCurrency = this.currencyService.getDefaultCurrency();
  }

  private loadCurrencies() {
    this.currencies = this.currencyService.getCurrencies().map((currency) => {
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

  onOptionSelected(currency: CurrencyResponse): void {
    const dialogRef = this.dialog.open(CurrencyDialogComponent, {
      width: '300px',
      data: { currency: currency.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.snackBar.open(
        'Default currency update ' +
          (result ? 'was' : "wasn't") +
          ' successful!',
        '',
        { duration: 3000 }
      );
    });
  }
}
