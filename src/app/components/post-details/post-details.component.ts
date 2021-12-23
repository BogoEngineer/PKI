import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';
import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

import recommendations from '../../../data/recommendations.json';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: any;
  panelOpenState = false;
  commentBool = false;
  comment = {
    rating: "",
    text: ""
  }
  recommend = ""

  constructor(
    private router: Router,
    private adminService: AdminService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.post = JSON.parse(localStorage.getItem("post"))
  }

  leaveComment() {
    this.commentBool = !this.commentBool
  }

  confirm() {
    this.post.comments.push(this.comment)
    this.commentBool = false;
    this.snackBar.open('Thanks for leaving a comment!', 'X', {
      duration: 3000
    });
  }

  recommendFunc(){
    var recommendation = {
      "bookId" : this.post.id,
      "usernameTo" : this.recommend,
      "usernameFrom" : JSON.parse(localStorage.getItem('user')).username
    }
    recommendations.push(recommendation)
    this.snackBar.open('You recommended a book!', 'X', {duration: 2000})
  }
}
