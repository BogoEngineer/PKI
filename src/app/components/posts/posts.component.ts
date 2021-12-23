import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin.service';
import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { stringify } from 'querystring';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import recommendations from '../../../data/recommendations.json';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  @Input() role: string;
  @Input() section: string;
  posts: any[];
  unfilteredPosts: any[];
  form = null;
  search = ""
  recommendedToMe: any;

  constructor(
    private router: Router,
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.posts = JSON.parse(localStorage.getItem("book"))
    this.unfilteredPosts = this.posts;
    if (this.section == 'promotion') this.posts = this.posts.filter(post => {
      if (post.promotion) return post
    })

    if (this.section == 'recommended') {
      var user = JSON.parse(localStorage.getItem('user'))
      this.recommendedToMe = recommendations.filter(recomm =>{
        if(recomm.usernameTo === user.username) return recomm.bookId;
      }).map(recomm => recomm.bookId)
      this.posts = this.posts.filter(post => {
        if(this.recommendedToMe.includes(post.id)) return post
      })
    }
    this.role = JSON.parse(localStorage.getItem('user')).admin ? "admin" : "user"
  }

  addNew(str: any) {
    this.router.navigate([`admin/addnew`], { queryParams: { type: str } });
  }

  seePostDetails(post) {
    localStorage.setItem("post", JSON.stringify(post))
    this.router.navigate(['user/details'])
  }

  formatContent(content) {
    if (content.length > 300) return content.substring(0, 300) + '...'
    else return content;
  }

  searchFor() {
    console.log(this.search)
    this.posts = this.unfilteredPosts.filter(post => {
      console.log(post.name)
      if (post.name.toLowerCase().includes(this.search.toLowerCase())) return post;
    })
    console.log(this.posts)
  }

}
