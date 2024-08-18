import { Component } from '@angular/core';
import { AppMaterialModule } from '../../app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-toolbar',
  standalone: true,
  imports: [AppMaterialModule, CommonModule, RouterModule],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.css',
})
export class ToolbarComponent {
  toolbarButtons = [
    { name: 'Accounts', route: '/accounts' },
    { name: 'Transactions', route: '/transactions' },
    { name: 'Settings', route: '/settings' },
  ];
}
