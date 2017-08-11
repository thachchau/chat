import { Component, OnInit } from '@angular/core';

import { UserService } from "./../services/user-service.service";
import { Router } from "@angular/router";
import { User } from "./../models/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  localUserSignup = {
    email: '',
    username: '',
    password: '',
    cfm_password: ''
  }

  constructor(private _userService: UserService,
              private _router: Router) {}

  ngOnInit() {
  }

  signUp(params: any){
    console.log(params);
     var result = params.validationGroup.validate();
        if (result.isValid) {
          this._userService.signupfn(this.localUserSignup)
          .subscribe(data =>{
            if(data == true){
              this._router.navigate(['login']);
              alert('Register successfull !');
            }
            else{
              this.localUserSignup.cfm_password = '';
              this.localUserSignup.email = '';
              this.localUserSignup.password = '';
              this.localUserSignup.username = '';
              alert('Register fail, try again');
            }
          })
        } 
    
  }
}
