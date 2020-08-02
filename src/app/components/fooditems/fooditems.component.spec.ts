import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FooditemsComponent } from './fooditems.component';

describe('FooditemsComponent', () => {
  let component: FooditemsComponent;
  let fixture: ComponentFixture<FooditemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FooditemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FooditemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
