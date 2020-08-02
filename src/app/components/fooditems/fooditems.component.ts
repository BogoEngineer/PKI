import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-fooditems',
  templateUrl: './fooditems.component.html',
  styleUrls: ['./fooditems.component.css']
})
export class FooditemsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'description', 'delete'];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  fooditems: any[];

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.adminService.getFoodItems().subscribe(res=>{
      this.fooditems = res.data;
      this.dataSource = new MatTableDataSource(this.fooditems);
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

  delete(fooditem:any){
    this.adminService.deleteFoodItem(fooditem).subscribe(res=>{
      this.fooditems = this.fooditems.filter((fi)=>{
        if(fi._id != fooditem._id) return fi;
      })
      this.dataSource = new MatTableDataSource(this.fooditems);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.snackBar.open(`Food item ${fooditem.name} has been deleted`, 'x', {
        duration: 1000,
        verticalPosition: "bottom"
      })
    })
  }

  truncate(str, n){
    return (str.length > n) ? str.substr(0, n-1) + '...' : str;
  };
}
