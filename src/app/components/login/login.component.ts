import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';

import allBooks from '../../../data/AllBooks.json';
import users from '../../../data/users.json';

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
    if(this.user.email == "" || this.user.password==""){
      this.snackBar.open("Please fill both fields!", 'x', {
        duration: 1500,
        verticalPosition: "bottom"
      })
      return;
    }

    localStorage.setItem("book", JSON.stringify(allBooks))
    var usrs = users
    var user = usrs.filter(usr=>{
      if(usr.username == this.user.email && usr.password == this.user.password) return usr
    })[0]
    localStorage.setItem("user", JSON.stringify(user))
    if(user == null) this.snackBar.open('Wrong credentials!', 'X', {duration: 2000})
    if(user.admin){
      this.router.navigate(['admin']);
      return;
    } 
    this.router.navigate(['user']);
    return;

  }
}
