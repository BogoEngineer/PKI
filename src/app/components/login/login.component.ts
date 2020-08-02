import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide=true;

  user: {
    email: string,
    password: string
  }

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.user = {
      email: "",
      password: ""
    }
  }

  logIn(){

  }
}
