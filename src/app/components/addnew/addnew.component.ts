import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { Component, OnInit, Input, ViewChild, ElementRef, ViewChildren, QueryList } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-addnew',
  templateUrl: './addnew.component.html',
  styleUrls: ['./addnew.component.css']
})
export class AddnewComponent implements OnInit {
  type: String;
  showPreview = false;

  confirm_forward = false;
  hide = false;
  meal_plans: any[];
  food_choices: any[];

  time_hashmap = {"before breakfast": 1, "after breakfast": 2, "am snack": 3,
  "before lunch": 4, "after lunch": 5, "pm snack": 6, "before dinner": 7, "after dinner": 8};
  preview_supplement_list = [];

  patient = {
    first_name: "",
    last_name: "",
    email: "",
    password: "1234",
    phase: null
  }

  phase = {
    phase_number: 1,
    meal_plan: null,
    food_choice: null,
    supplement_plan: null,
    exception: ""
  }

  supplement_plan = {
    before_breakfast: null,
    after_breakfast: null,
    am_snack: null,
    before_lunch: null,
    after_lunch: null,
    pm_snack: null,
    before_dinner: null,
    after_dinner: null
  }

  supplement = {
    name: "",
    manufacturer: "",
    description: ""
  }

  fooditem = {
    name: "",
    description: ""
  }

  foodchoice = {
    name: "",
    allowed: [],
    not_allowed: []
  }

  mealplan = {
    name: "",
    description: ""
  }

  post = {
    name: "",
    content: "",
    image: null
  }

  times: String[] = ["before breakfast", "after breakfast", "am snack",
   "before lunch", "after lunch", "pm snack", "before dinner", "after dinner"]
  
  permissions: String[] = ["allowed", "not_allowed"];

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(
    private _formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.type = this.route.snapshot.queryParams.type;
    if(this.type == 'user'){
      this.adminService.getMealPlans().subscribe(res=>{
        this.meal_plans = res.data;
      })
      this.adminService.getFoodChoices().subscribe(res=>{
        this.food_choices = res.data;
      })
    }
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
      secondCtrl: ['', Validators.required],
      thirdCtrl: ['', Validators.required],
      fourthCtrl: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
      secondCtrl: ['', Validators.required],
      thirdCtrl: ['', Validators.required],
      fourthCtrl: ['', Validators.required]
    });
  }

  confirm(){
    switch(this.type){
      case 'user': 
        this.adminService.createNewSupplementPlan(this.supplement_plan).subscribe(res=>{
          this.phase.supplement_plan = res.data._id;
          this.adminService.createNewPhase(this.phase).subscribe(res=>{
            this.patient.phase = res.data._id;
            this.adminService.createNewUser(this.patient).subscribe(res=>{
              this.router.navigate(['admin'], {queryParams: {event: "addedUser"}});
            })
          })
        });
        break;
      case 'supplement':
        this.adminService.createNewSupplement(this.supplement).subscribe(res=>{
          this.router.navigate(['admin'], {queryParams: {event: "addedSupplement"}});
        });
        break;
      case 'fooditem':
        this.adminService.createNewFoodItem(this.fooditem).subscribe(res=>{
          this.router.navigate(['admin'], {queryParams: {event: "addedFoodItem"}});
        });
        break;
      case 'foodchoice':
        this.adminService.createNewFoodChoice(this.foodchoice).subscribe(res=>{
          this.router.navigate(['admin'], {queryParams: {event: "addedFoodChoice"}});
        });
        break;
      case 'mealplan':
        this.adminService.createNewMealPlan(this.mealplan).subscribe(res=>{
          this.router.navigate(['admin'], {queryParams: {event: "addedMealPlan"}});
        });
        break;
      case 'post':
        this.adminService.createNewPost(this.post).subscribe(res=>{
          this.router.navigate(['admin'], {queryParams: {event: "addedPost"}});
        });
        break;
    }
  }

  previewToggle(){
    this.showPreview = !this.showPreview;
  }

  getMealPlanName(id){
    return this.meal_plans.filter(meal_plan=>{
      if(meal_plan._id == id) return meal_plan
    })[0] ? this.meal_plans.filter(meal_plan=>{
      if(meal_plan._id == id) return meal_plan
    })[0].name : "";
  }

  getFoodChoiceName(id){
    return this.food_choices.filter(food_choice=>{
      if(food_choice._id == id) return food_choice
    })[0].name;
  }

  saveSuppl(){
    this.confirm_forward = true;
  }

  getPreview(preview){
    let splited = preview.title.split(" ");
    this.supplement_plan[splited[0] + "_" + splited[1]] = preview.data.map(supplement=>supplement._id);
    this.preview_supplement_list[this.time_hashmap[preview.title]-1] = {title: preview.title, data: preview.data}
  }

  getItems(items){
    if(items.title == 'not allowed'){
      this.foodchoice.not_allowed = items.data;
    }
    else{
      this.foodchoice.allowed = items.data;
    }
  }

  previewToString(supplements){
    let ret_str = "";
    supplements.forEach(element => {
      ret_str += element.name + "(" + element.manufacturer + ")" + ", ";
    });
    return ret_str.slice(0, ret_str.length-2);
  }

  csvInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
    if(!fileInputEvent.target.files[0].type.includes("image")) {
      this.snackBar.open("The uploaded file needs to have an image type.", 'x', {
        verticalPosition: "bottom"
      })
      return;
    }
  }
}
