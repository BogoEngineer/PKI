import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { MatChipInput, MatChipInputEvent } from '@angular/material/chips';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { FormControl } from '@angular/forms';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-choose-fooditems',
  templateUrl: './choose-fooditems.component.html',
  styleUrls: ['./choose-fooditems.component.css']
})
export class ChooseFooditemsComponent implements OnInit {
  @Input() title: any;
  @Input() confirmed: any;
  @Output() items: EventEmitter<any> = new EventEmitter(); 

  all_fooditems: any[];
  visible = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fooditemCtrl = new FormControl();
  filteredfooditems: Observable<string[]>;
  fooditems: any[] = [];
  @ViewChild('fooditemInput') set ft(fooditemInput: ElementRef<HTMLInputElement>){
    if(fooditemInput){
      this.fooditemInputFirst = fooditemInput;
    }
  };
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  fooditemInputFirst: ElementRef<HTMLInputElement>;

  constructor(    
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.adminService.getFoodItems().subscribe(res=>{
      this.all_fooditems = res.data;
      this.filteredfooditems = this.fooditemCtrl.valueChanges.pipe(
        startWith(null),
        map((fooditem: any | null) => fooditem ? this._filter(fooditem) : this.all_fooditems.slice()));
    })
  }

  notFocused(){
    this.items.emit({title: this.title, data: this.fooditems});
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    //console.log("EVENT", event);

    // Add our fruit
    if ((value || '')) {
      this.fooditems.push(value);
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.fooditemCtrl.setValue(null);
  }

  remove(fooditem: string): void {
    const index = this.fooditems.indexOf(fooditem);

    if (index >= 0) {
      this.fooditems.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let keyword = event.option.viewValue
    this.fooditems.push(this.all_fooditems.filter(fooditem=>{
      if(fooditem.name == keyword) return fooditem;
    })[0]);
    this.fooditemInputFirst.nativeElement.value = '';
    this.fooditemCtrl.setValue(null);
  }

  private _filter(value: any): any[] {
    let filterValue;
    if(!value._id){
      filterValue = value;
      return this.all_fooditems.filter(fooditem=>{
        let reference = fooditem.name
        if(reference.includes(filterValue) || reference.toLocaleLowerCase().includes(filterValue)||
        reference.toLocaleUpperCase().includes(filterValue)) return fooditem;
      })
    }
    filterValue = value._id;
    return this.all_fooditems.filter(fooditem=> {
      if(fooditem._id != filterValue) return fooditem;
    });
  }

}
