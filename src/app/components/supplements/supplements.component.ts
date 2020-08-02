import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-supplements',
  templateUrl: './supplements.component.html',
  styleUrls: ['./supplements.component.css']
})
export class SupplementsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'manufacturer', 'description', 'delete'];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  supplements: any[];

  constructor(
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.adminService.getSupplements().subscribe(res=>{
      this.supplements = res.data;
      this.dataSource = new MatTableDataSource(this.supplements);
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

  showSpecifications(supplement:any){
    console.log(supplement);
  }

  addNew(str:any){
    this.router.navigate([`admin/addnew`], {queryParams: {type: str}});
  }

  delete(supplement:any){
    this.adminService.deleteSupplement(supplement).subscribe(res=>{
      this.supplements = this.supplements.filter((supp)=>{
        if(supp._id != supplement._id) return supp;
      })
      this.dataSource = new MatTableDataSource(this.supplements);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.snackBar.open(`Supplement ${supplement.name} has been deleted`, 'x', {
        duration: 1000,
        verticalPosition: "bottom"
      })
    });
  }

  truncate(str, n){
    return (str.length > n) ? str.substr(0, n-1) + '...' : str;
  };
}
