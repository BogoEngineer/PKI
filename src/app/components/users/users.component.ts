import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from 'src/app/services/admin.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  displayedColumns: string[] = ['name', 'email', 'phase', 'appointment', 'delete'];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  users: any[];

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.adminService.getUsers().subscribe(res=>{
      this.users = res.data;
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  ngAfterViewInit() {
  }
    

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  showSpecifications(user:any){
    console.log(user);
  }

  addNew(str:any){
    this.router.navigate([`admin/addnew`], {queryParams: {type: str}});
  }

  delete(user:any){
    this.adminService.deleteUser(user).subscribe(res=>{
      this.users = this.users.filter((usr)=>{
        if(usr._id != user._id) return usr;
      })
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.snackBar.open(`User ${user.first_name} ${user.last_name} has been deleted`, 'x', {
        duration: 1000,
        verticalPosition: "bottom"
      })
    });
  }

  appointment(user:any){
    this.router.navigate(['admin/users/appointment'], {state: {user: user}});
  }
}
