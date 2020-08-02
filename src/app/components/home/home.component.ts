import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

const DURATION = 1500;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(    
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if(this.route.snapshot.queryParams){
      switch(this.route.snapshot.queryParams.event){
        case "addedUser":
          this.snackBar.open(`New user has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
        case "addedSupplement":
          this.snackBar.open(`New supplement has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
        case "addedFoodItem":
          this.snackBar.open(`New food item has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
        case "addedFoodChoice":
          this.snackBar.open(`New food choice has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
        case "addedMealPlan":
          this.snackBar.open(`New meal plan has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
        case "addedPost":
          this.snackBar.open(`New post has been added!`, 'x', {
            duration: DURATION,
            verticalPosition: "bottom"
          });
          break;
      }
    }
    this.router.navigate([`admin`]);
  }
}
