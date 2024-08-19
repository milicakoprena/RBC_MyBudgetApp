import { Component, ViewChild } from '@angular/core';
import {
  NavigationEnd,
  Router,
  RouterModule,
  RouterOutlet,
} from '@angular/router';
import { ToolbarComponent } from './custom-components/toolbar/toolbar.component';
import { AppMaterialModule } from './app-material/app-material.module';
import { FooterComponent } from './custom-components/footer/footer.component';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs';
import { TransactionsComponent } from './transactions/transactions.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HttpClientModule,
    CommonModule,
    RouterOutlet,
    ToolbarComponent,
    FooterComponent,
    RouterModule,
    AppMaterialModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'mybudgetapp';
  showFooter: boolean = true;

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const routesWithFooter = ['/accounts', '/transactions'];
        this.showFooter = routesWithFooter.includes(event.urlAfterRedirects);
      });
  }
}
