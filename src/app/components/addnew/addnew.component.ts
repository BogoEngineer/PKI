import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit, Input, ViewChild, ElementRef, ViewChildren, QueryList } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';

import allBooks from '../../../data/AllBooks.json';


@Component({
  selector: 'app-addnew',
  templateUrl: './addnew.component.html',
  styleUrls: ['./addnew.component.css']
})
export class AddnewComponent implements OnInit {
  type: String;
  showPreview = false;

  confirm_forward = false;
  hide = false;

  book = {
    id: Math.max(allBooks.map(book => book.id)) + 1,
    name: "",
    content: "",
    image: null,
    author: "",
    year: "",
    pages: "",
    promotion: false,
    comments: []
  }

  permissions: String[] = ["allowed", "not_allowed"];

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService,
    private router: Router,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  confirm() {
    allBooks.push(this.book);
    this.snackBar.open("You added a book!", 'X', {
      verticalPosition: "top",
      duration: 2000
    })
    localStorage.setItem('book', JSON.stringify(allBooks))
  }

  previewToggle() {
    this.showPreview = !this.showPreview;
  }

  previewToString(supplements) {
    let ret_str = "";
    supplements.forEach(element => {
      ret_str += element.name + "(" + element.manufacturer + ")" + ", ";
    });
    return ret_str.slice(0, ret_str.length - 2);
  }

  csvInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
    if (!fileInputEvent.target.files[0].type.includes("image")) {
      this.snackBar.open("The uploaded file needs to have an image type.", 'x', {
        verticalPosition: "bottom"
      })
      return;
    }
  }
}
