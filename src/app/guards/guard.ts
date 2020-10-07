import { Injectable } from "@angular/core";
import { Router, CanActivate } from '@angular/router';

@Injectable()
export class Guard implements CanActivate{
    constructor(
        private router: Router
    ){}

    canActivate(){
        let token = localStorage.getItem('jwt');
        if(token != null){
            return true;
        }
        this.router.navigate([''])
        return false;
    }

}