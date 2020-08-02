import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementsComponent } from './supplements.component';

describe('SupplementsComponent', () => {
  let component: SupplementsComponent;
  let fixture: ComponentFixture<SupplementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
