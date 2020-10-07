import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  posts : any[];


  constructor(
    private router: Router,
    private adminService: AdminService
    ) { }

  ngOnInit(): void {
    this.adminService.getPosts().subscribe(res=>{
      this.posts = res.data;
    })
  }

  addNew(str:any){
    this.router.navigate([`admin/addnew`], {queryParams: {type: str}});
  }

}
