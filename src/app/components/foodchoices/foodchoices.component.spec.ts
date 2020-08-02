import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodchoicesComponent } from './foodchoices.component';

describe('FoodchoicesComponent', () => {
  let component: FoodchoicesComponent;
  let fixture: ComponentFixture<FoodchoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodchoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodchoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
