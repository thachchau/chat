import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, RequestOptionsArgs } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/Observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/toPromise';

import { User } from "./../models/user";


@Injectable()
export class UserService {

  private url: string;

  isLoggedin: boolean;
  constructor(private _http: Http) { }

  loginfn(usercreds: any) {
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/authLogin';
    let headers = new Headers([ {'Content-Type': 'application/json'}, {'Accept' : 'application/json'}]);
    let options = new RequestOptions({ headers: headers });
    return this._http.post(this.url,usercreds, options)
               .map((data: Response) => {
                 let response = data.json();
                 return response;
               });
  }

  signupfn(newUser: any){
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/register';
    let headers = new Headers([
      {'Content-Type': 'application/json'},
      {'Accept': 'application/json'}
    ]);
    let options = new RequestOptions({headers: headers});
    return this._http.post(this.url, newUser, options)
    .map((data: Response) =>{
      let response = data.json();
      if(response == "register OK") return true;
      if(response == "register NOK") return false;
    });
  }

  logoutfn(username: any){
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/logout';
     let headers = new Headers([
      {'Content-Type': 'application/json'},
      {'Accept': 'application/json'}
    ]);
    let options = new RequestOptions({headers: headers});
    return this._http.post(this.url,username, options)
               .map((data: Response) => {
                 let response = data.json();
                 return response;
               });
  }

  getUserOnlineList(){
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/user-online';
    return this._http.get(this.url)
      .map((data: Response) => {
        let response = data.json();
        return response;
      });
  }

  updateAvatar(username: string, image: string){
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/update-avatar';
    let data = {username: username, image: image};
    let headers = new Headers([ {'Content-Type': 'application/json'}, {'Accept' : 'application/json'}]);
    let options = new RequestOptions({ headers: headers });
    return this._http.post(this.url, data, options)
    .map((data: Response) =>{
      let response = data.json();
      return response;
    });
  }

  getAvatar(username: string){
    this.url = 'http://10.0.0.206:8080/cntc/rest/user/get-avatar?username=' + username;
    return this._http.get(this.url)
      .map((data: Response) => {
        let response = data.json();
        return response;
      });
  }

  
}