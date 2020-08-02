import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

// Angular Material
import { MatTooltipModule} from '@angular/material/tooltip';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatStepperModule} from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatListModule} from '@angular/material/list';
import {MatTabsModule} from '@angular/material/tabs';
import {MatBadgeModule} from '@angular/material/badge';
import {MatChipsModule} from '@angular/material/chips';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {AdminService } from './services/admin.service';
import { UsersComponent } from './components/users/users.component'
import { SupplementsComponent } from './components/supplements/supplements.component';
import { FooditemsComponent } from './components/fooditems/fooditems.component';
import { FoodchoicesComponent } from './components/foodchoices/foodchoices.component';
import { PostsComponent } from './components/posts/posts.component';
import { HomeComponent } from './components/home/home.component';
import { AddnewComponent } from './components/addnew/addnew.component';
import { ChooseSupplementsComponent } from './components/choose-supplements/choose-supplements.component';
import { ChooseFooditemsComponent } from './components/choose-fooditems/choose-fooditems.component';
import { MealplansComponent } from './components/mealplans/mealplans.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AppointmentComponent } from './components/appointment/appointment.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'admin', component: HomeComponent },
  { path: 'admin/users', component: UsersComponent },
  { path: 'admin/supplements', component: SupplementsComponent },
  { path: 'admin/fooditems', component: FooditemsComponent },
  { path: 'admin/foodchoices', component: FoodchoicesComponent },
  { path: 'admin/posts', component: PostsComponent },
  { path: 'admin/addnew', component: AddnewComponent },
  { path: 'admin/changepassword', component: ChangePasswordComponent },
  { path: 'admin/users/appointment', component: AppointmentComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    UsersComponent,
    SupplementsComponent,
    FoodchoicesComponent,
    FooditemsComponent,
    PostsComponent,
    HomeComponent,
    AddnewComponent,
    ChooseSupplementsComponent,
    ChooseFooditemsComponent,
    MealplansComponent,
    ChangePasswordComponent,
    AppointmentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTooltipModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    MatSnackBarModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    HttpClientModule,
    MatPaginatorModule,
    MatSortModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatBadgeModule,
    MatTabsModule,
    MatChipsModule,
    MatAutocompleteModule,
    NgbModule 
  ],
  providers: [AdminService],
  bootstrap: [AppComponent]
})
export class AppModule { }
