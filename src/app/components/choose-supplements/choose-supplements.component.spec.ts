import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseSupplementsComponent } from './choose-supplements.component';

describe('ChooseSupplementsComponent', () => {
  let component: ChooseSupplementsComponent;
  let fixture: ComponentFixture<ChooseSupplementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseSupplementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseSupplementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
