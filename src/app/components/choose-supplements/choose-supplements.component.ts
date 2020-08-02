import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { MatChipInput, MatChipInputEvent } from '@angular/material/chips';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { FormControl } from '@angular/forms';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-choose-supplements',
  templateUrl: './choose-supplements.component.html',
  styleUrls: ['./choose-supplements.component.css']
})
export class ChooseSupplementsComponent implements OnInit {
  @Input() title: any;
  @Input() confirmed: boolean;
  @Output() preview: EventEmitter<any> = new EventEmitter(); 

  all_supplements: any[];
  visible = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  supplementCtrl = new FormControl();
  filteredSupplements: Observable<string[]>;
  supplements: any[] = [];
  @ViewChild('supplementInput') set ft(supplementInput: ElementRef<HTMLInputElement>){
    if(supplementInput){
      this.supplementInputFirst = supplementInput;
    }
  };
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  supplementInputFirst: ElementRef<HTMLInputElement>;

  constructor(    
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.adminService.getSupplements().subscribe(res=>{
      this.all_supplements = res.data;
      this.filteredSupplements = this.supplementCtrl.valueChanges.pipe(
        startWith(null),
        map((supplement: any | null) => supplement ? this._filter(supplement) : this.all_supplements.slice()));
    })
  }

  ngOnChanges() {
    if(!!this.confirmed){         
      this.preview.emit({title: this.title, data: this.supplements});
    }
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    //console.log("EVENT", event);

    // Add our fruit
    if ((value || '')) {
      this.supplements.push(value);
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.supplementCtrl.setValue(null);
  }

  remove(supplement: string): void {
    const index = this.supplements.indexOf(supplement);

    if (index >= 0) {
      this.supplements.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let keywords = event.option.viewValue.split(', ');
    this.supplements.push(this.all_supplements.filter(supplement=>{
      if(supplement.name == keywords[0] && supplement.manufacturer == keywords[1]) return supplement;
    })[0]);
    this.supplementInputFirst.nativeElement.value = '';
    this.supplementCtrl.setValue(null);
  }

  private _filter(value: any): any[] {
    let filterValue;
    if(!value._id){
      filterValue = value;
      return this.all_supplements.filter(supplement=>{
        let reference = supplement.name + ', ' + supplement.manufacturer;
        if(reference.includes(filterValue) || reference.toLocaleLowerCase().includes(filterValue)||
        reference.toLocaleUpperCase().includes(filterValue)) return supplement;
      })
    }
    filterValue = value._id;
    return this.all_supplements.filter(supplement=> {
      if(supplement._id != filterValue) return supplement;
    });
  }

}
