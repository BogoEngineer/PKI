import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-mealplans',
  templateUrl: './mealplans.component.html',
  styleUrls: ['./mealplans.component.css']
})
export class MealplansComponent implements OnInit {
  displayedColumns: string[] = ['name', 'description', 'delete'];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  mealplans: any[];

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.adminService.getMealPlans().subscribe(res=>{
      this.mealplans = res.data;
      this.dataSource = new MatTableDataSource(this.mealplans);
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

  showSpecifications(fooditem:any){
    console.log(fooditem);
  }

  addNew(str:any){
    this.router.navigate([`admin/addnew`], {queryParams: {type: str}});
  }

  delete(mealplan:any){
    this.adminService.deleteMealPlan(mealplan).subscribe(res=>{
      this.mealplans = this.mealplans.filter((fi)=>{
        if(fi._id != mealplan._id) return fi;
      })
      this.dataSource = new MatTableDataSource(this.mealplans);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.snackBar.open(`Food item ${mealplan.name} has been deleted`, 'x', {
        duration: 1000,
        verticalPosition: "bottom"
      })
    })

  }

  truncate(str, n){
    return (str.length > n) ? str.substr(0, n-1) + '...' : str;
  };
}

