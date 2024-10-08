import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAccountDialogComponent } from './new-account-dialog.component';

describe('NewAccountDialogComponent', () => {
  let component: NewAccountDialogComponent;
  let fixture: ComponentFixture<NewAccountDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewAccountDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewAccountDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
