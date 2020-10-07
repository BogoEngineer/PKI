import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  url:string = "http://localhost:5000";

  constructor(
    private http:HttpClient
  ) { }

  login(credentials: any){
    const url = `${this.url}/admin/authenticate`;
    return this.http.post<any>(url, credentials);
  }

  getUsers(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/users`;
    return this.http.get<any>(url, header);
  }

  getSupplements(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/supplements`;
    return this.http.get<any>(url, header);
  }

  getFoodItems(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/fooditems`;
    return this.http.get<any>(url, header);
  }

  getFoodChoices(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/foodchoices`;
    return this.http.get<any>(url, header);
  }

  getMealPlans(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/mealplans`;
    return this.http.get<any>(url, header);
  }

  getPosts(){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/user/posts`;
    return this.http.get<any>(url, header);
  }

  deleteUser(user:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/users/${user._id}`;
    return this.http.delete<any>(url, header);
  }

  deleteSupplement(supplement:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/supplements/${supplement._id}`;
    return this.http.delete<any>(url, header);
  }

  deleteFoodChoice(foodchoice:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/foodchoices/${foodchoice._id}`;
    return this.http.delete<any>(url, header);
  }

  deleteMealPlan(mealplan:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/mealplans/${mealplan._id}`;
    return this.http.delete<any>(url, header);
  }

  deleteFoodItem(fooditem:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/fooditems/${fooditem._id}`;
    return this.http.delete<any>(url, header);
  }

  createNewUser(user:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/users`;
    return this.http.post<any>(url, user, header);
  }

  createNewPhase(phase:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/phases`;
    return this.http.post<any>(url, phase, header);
  }

  createNewSupplementPlan(supplementPlan:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/supplementplans`;
    return this.http.post<any>(url, supplementPlan, header);
  }

  createNewSupplement(supplement:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/supplements`;
    return this.http.post<any>(url, supplement, header);
  }

  createNewFoodItem(fooditem:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/fooditems`;
    return this.http.post<any>(url, fooditem, header);
  }

  createNewFoodChoice(foodchoice:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/foodchoices`;
    return this.http.post<any>(url, foodchoice, header);
  }

  createNewMealPlan(mealplan:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/mealplans`;
    return this.http.post<any>(url, mealplan, header);
  }

  createNewPost(post:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/posts`;
    return this.http.post<any>(url, post, header);
  }

  updateUser(userId:any, phaseId:any){
    let jwt = localStorage.getItem("jwt")
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  jwt)
    }
    const url = `${this.url}/admin/users/${userId}`;
    return this.http.put<any>(url, {id: phaseId}, header);
  }
}
