import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { CurrencyService } from '../../services/currency.service';

@Component({
  selector: 'app-currency-dialog',
  standalone: true,
  imports: [AppMaterialModule],
  templateUrl: './currency-dialog.component.html',
  styleUrl: './currency-dialog.component.css',
})
export class CurrencyDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CurrencyDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { currency: string },
    private currencyService: CurrencyService
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.currencyService.updateDefaultCurrency(this.data.currency).subscribe(
      (response) => this.dialogRef.close(true),
      (error) => {
        console.log('Currency update failed', error);
        this.dialogRef.close(false);
      }
    );
  }
}
