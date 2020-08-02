import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from 'src/app/services/admin.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-foodchoices',
  templateUrl: './foodchoices.component.html',
  styleUrls: ['./foodchoices.component.css']
})
export class FoodchoicesComponent implements OnInit {
  displayedColumns: string[] = ['name', 'delete'];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  foodchoices: any[];

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.adminService.getFoodChoices().subscribe(res=>{
      this.foodchoices = res.data;
      this.dataSource = new MatTableDataSource(this.foodchoices);
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

  showSpecifications(foodchoice:any){
    console.log(foodchoice);
  }

  addNew(str:any){
    this.router.navigate([`admin/addnew`], {queryParams: {type: str}});
  }

  delete(foodchoice:any){
      this.adminService.deleteFoodChoice(foodchoice).subscribe(res=>{
        this.foodchoices = this.foodchoices.filter((fc)=>{
          if(fc._id != foodchoice._id) return fc;
        })
        this.dataSource = new MatTableDataSource(this.foodchoices);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.snackBar.open(`Foodchoice ${foodchoice.name} has been deleted`, 'x', {
          duration: 1000,
          verticalPosition: "bottom"
        })
      });
  }
}
