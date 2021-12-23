import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  user: any;
  hide1= true;
  hide2= true;

  constructor(
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'))
  }

  confirm(){
    localStorage.setItem("user", JSON.stringify(this.user))
    this.snackBar.open('Info succesfully changed!', 'X', {duration: 2000})
  }
}
