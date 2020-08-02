import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {

  patient: any;

  profile_picture = "../../../assets/anonuser.png" || this.patient.profile_picture

  constructor(
    private adminService:AdminService,
    private _formBuilder: FormBuilder,
    private router: Router
  ) { }
  
  times = [
    "before_breakfast",
    "after_breakfast",
    "am_snack",
    "before_lunch",
    "after_lunch",
    "pm_snack",
    "before_dinner",
    "after_dinner"
  ]

  confirm_forward = false;

  meal_plans: any[];
  food_choices: any[];

  update_phase = {
    phase_number: 1,
    meal_plan: null,
    food_choice: null,
    exception: "",
    supplement_plan: {
      before_breakfast: null,
      after_breakfast: null,
      am_snack: null,
      before_lunch: null,
      after_lunch: null,
      pm_snack: null,
      before_dinner: null,
      after_dinner: null
    }
  }
  secondFormGroup: FormGroup;
  update = false;

  ngOnInit(): void {
    this.patient = history.state.user || JSON.parse(localStorage.getItem('patient'));
    localStorage.setItem('patient', JSON.stringify(this.patient))
    this.secondFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
      secondCtrl: ['', Validators.required],
      thirdCtrl: ['', Validators.required],
      fourthCtrl: ['', Validators.required]
    });
  }

  ngAfterViewInit(){
    this.adminService.getMealPlans().subscribe(res=>{
      this.meal_plans = res.data;
    })
    this.adminService.getFoodChoices().subscribe(res=>{
      this.food_choices = res.data;
    })
  }

  updateToggle(){
    this.update = !this.update;
  }

  saveSuppl(){
    this.confirm_forward = true
  }

  confirm(){
    this.adminService.createNewSupplementPlan(this.update_phase.supplement_plan).subscribe(res=>{
      this.update_phase.supplement_plan = res.data._id;
      this.adminService.createNewPhase(this.update_phase).subscribe(res=>{
        this.patient.phase = res.data;
        this.adminService.updateUser(this.patient._id, res.data._id).subscribe(res=>{
          this.updateToggle();
          this.router.navigate(['admin']);
        });
      })
    })
  }

  arrayToStr(array){
    let ret_str="";
    array.forEach(element => {
      ret_str += element.name + (element.manufacturer? '(' + element.manufacturer + ')' : '') + ', ';
    });
    return ret_str.slice(0, ret_str.length-2);
  }

  beautify(time){
    return time.split('_')[0] + ' ' + time.split('_')[1];
  }

  getPreview(preview){
    let splited = preview.title.split(" ");
    this.update_phase.supplement_plan[splited[0] + "_" + splited[1]] = preview.data.map(supplement=>supplement._id);
  }
}
