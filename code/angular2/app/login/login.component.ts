import { Component, OnInit } from '@angular/core';

import { UserService } from "./../services/user-service.service";
import { Router } from "@angular/router";
import { Http } from "@angular/http";
import { ChatService } from "./../services/chat.service";
import { User } from "./../models/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  localUserLogin = {
    username: '',
    password: ''
  }

  isLoginOK: boolean = true;

  constructor(private _userService: UserService,
    private _router: Router, private _chatService: ChatService) { }

  ngOnInit() {

  }

  login() {
    this._userService.loginfn(this.localUserLogin)
      .subscribe(data => {
        if (data.data.username != null) {
          console.log(data);
          this._router.navigate(['home']);
          this._chatService.setUsername(data.data.username);
        }
        else {
          this.isLoginOK = false;
          this.localUserLogin.password = '';
        }
      });
  }
}
