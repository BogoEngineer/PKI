import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  url:string = "http://localhost:5000";

  constructor(
    private http:HttpClient
  ) { }

  getUsers(){
    const url = `${this.url}/admin/users`;
    return this.http.get<any>(url);
  }

  getSupplements(){
    const url = `${this.url}/admin/supplements`;
    return this.http.get<any>(url);
  }

  getFoodItems(){
    const url = `${this.url}/admin/fooditems`;
    return this.http.get<any>(url);
  }

  getFoodChoices(){
    const url = `${this.url}/admin/foodchoices`;
    return this.http.get<any>(url);
  }

  getMealPlans(){
    const url = `${this.url}/admin/mealplans`;
    return this.http.get<any>(url);
  }

  deleteUser(user:any){
    const url = `${this.url}/admin/users/${user._id}`;
    return this.http.delete<any>(url);
  }

  deleteSupplement(supplement:any){
    const url = `${this.url}/admin/supplements/${supplement._id}`;
    return this.http.delete<any>(url);
  }

  deleteFoodChoice(foodchoice:any){
    const url = `${this.url}/admin/foodchoices/${foodchoice._id}`;
    return this.http.delete<any>(url);
  }

  deleteMealPlan(mealplan:any){
    const url = `${this.url}/admin/mealplans/${mealplan._id}`;
    return this.http.delete<any>(url);
  }

  deleteFoodItem(fooditem:any){
    const url = `${this.url}/admin/fooditems/${fooditem._id}`;
    return this.http.delete<any>(url);
  }

  createNewUser(user:any){
    const url = `${this.url}/admin/users`;
    return this.http.post<any>(url, user);
  }

  createNewPhase(phase:any){
    const url = `${this.url}/admin/phases`;
    return this.http.post<any>(url, phase);
  }

  createNewSupplementPlan(supplementPlan:any){
    const url = `${this.url}/admin/supplementplans`;
    return this.http.post<any>(url, supplementPlan);
  }

  createNewSupplement(supplement:any){
    const url = `${this.url}/admin/supplements`;
    return this.http.post<any>(url, supplement);
  }

  createNewFoodItem(fooditem:any){
    const url = `${this.url}/admin/fooditems`;
    return this.http.post<any>(url, fooditem);
  }

  createNewFoodChoice(foodchoice:any){
    const url = `${this.url}/admin/foodchoices`;
    return this.http.post<any>(url, foodchoice);
  }

  createNewMealPlan(mealplan:any){
    const url = `${this.url}/admin/mealplans`;
    return this.http.post<any>(url, mealplan);
  }

  createNewPost(post:any){
    const url = `${this.url}/admin/posts`;
    return this.http.post<any>(url, post);
  }

  updateUser(userId:any, phaseId:any){
    const url = `${this.url}/admin/users/${userId}`;
    return this.http.put<any>(url, {id: phaseId});
  }
}
