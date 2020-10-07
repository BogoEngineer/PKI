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
    if(this.user.email == "" || this.user.password==""){
      this.snackBar.open("Please fill both fields!", 'x', {
        duration: 1500,
        verticalPosition: "bottom"
      })
      return;
    }

    this.adminService.login(this.user).subscribe(res=>{
      if(res.success == true){
        localStorage.setItem("jwt", res.token)
        this.router.navigate(['admin'])
      }
      else{
        this.snackBar.open(res.msg, 'x', {
          duration: 1500,
          verticalPosition: "bottom"
        })
      }
    })
  }
}
