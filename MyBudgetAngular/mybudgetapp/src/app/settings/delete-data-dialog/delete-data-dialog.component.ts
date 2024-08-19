import { Component, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AccountService } from '../../services/account.service';
import { response } from 'express';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppMaterialModule } from '../../app-material/app-material.module';

@Component({
  selector: 'app-delete-data-dialog',
  standalone: true,
  imports: [AppMaterialModule],
  templateUrl: './delete-data-dialog.component.html',
  styleUrl: './delete-data-dialog.component.css',
})
export class DeleteDataDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteDataDialogComponent>,
    private accountService: AccountService,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.accountService.deleteAllAccounts().subscribe(
      (response) => {
        this.snackBar.open('All data was deleted successfully!', '', {
          duration: 3000,
        });
        this.dialogRef.close(true);
      },
      (error) => {
        console.log('Error deleting data', error);
        this.dialogRef.close(false);
      }
    );
  }
}
