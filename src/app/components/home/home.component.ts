import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

const DURATION = 1500;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router) { }

  search = "";
  ngOnInit(): void {
    
  }
}
