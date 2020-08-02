import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseFooditemsComponent } from './choose-fooditems.component';

describe('ChooseFooditemsComponent', () => {
  let component: ChooseFooditemsComponent;
  let fixture: ComponentFixture<ChooseFooditemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseFooditemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseFooditemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
