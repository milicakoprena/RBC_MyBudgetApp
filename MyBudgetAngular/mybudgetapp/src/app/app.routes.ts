import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'accounts',
    loadComponent: () =>
      import('./accounts/accounts.component').then((m) => m.AccountsComponent),
  },
  {
    path: 'transactions',
    loadComponent: () =>
      import('./transactions/transactions.component').then(
        (m) => m.TransactionsComponent
      ),
  },
  {
    path: 'settings',
    loadComponent: () =>
      import('./settings/settings.component').then((m) => m.SettingsComponent),
  },
  {
    path: '**',
    redirectTo: '/accounts',
    pathMatch: 'full',
  },
];
