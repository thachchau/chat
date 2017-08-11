import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';

import { Router } from "@angular/router";

import { UserService } from "./../services/user-service.service";
import { ChatService } from "./../services/chat.service";
import { User } from "./../models/user";
import { Http } from "@angular/http";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  useronlines: any = [];
  contactedusers: any = []
  messages: any = [];
  oldMessages: any = [];
  tmpOldMsgs: any = [];
  message: string;
  connection: any;
  loginedUser: string;
  objMessage: any;
  nameFriendChatting: string = null;
  isOpenHistory: boolean = false;
  isShowProfile = true;
  isSeeMore = true;
  count: number = 5;
  isEditProfile: boolean = false;
  tmp: any;

  nameImg: string = null;
  pathImg: any = '';
  isyourprofile: boolean = true;
  intUpdateAvatar: number = 0;

  currentUser = {
    id: null,
    email: null,
    username: null
  };

  selectUser = {
    selectUserid: null,
    selectUserEmail: null,
    selectUsername: null
  }

  flag: boolean = true;

  pathCoverImg: string;
  currentUserAvatar: string = null;
  isChoose: boolean = false;

  constructor(private _router: Router,
    private _userService: UserService, private _chatService: ChatService, private _http: Http) {
    this.pathCoverImg = "../res/cover.jpg";
    setInterval(() => {
      this.getUserOnlines();
    }, 15000);
  }

  ngOnInit() {
    this.loginedUser = this._chatService.getUsername();
    this.getCurrentUser();
    this._chatService.connectedServer(this.loginedUser);
    this.connection = this._chatService.getMessage()
      .subscribe(
      message => {
        this.objMessage = message;
        this._chatService.saveMessage(this.objMessage)
          .subscribe(data => {
            console.log(data);
          })
      }
      );
    this.getUserOnlines();
    //this.getRoom();
    this._userService.getAvatar(this.loginedUser)
    .subscribe(data=>{
      if(data.data.avatar == null){
        this.currentUserAvatar = '../../assets/res/avatar.jpg';
      }
      else{
        this.currentUserAvatar = 'data:image/png;base64,' + data.data.avatar;
      }
    })
  }

  ngOnDestroy() {
    this._chatService.discServer();
  }

  yourProfile(e, name:string): void{
    this.isyourprofile = true;
    this.getProfile(name);
    this._userService.getAvatar(name)
    .subscribe(data =>{
      console.log(data.data);
      this.tmp = data.data.avatar;
      this.nameImg = 'data:image/png;base64,' + data.data.avatar;
    });
  }

  friendProfile(e, name: string): void{
    this.isyourprofile = false;
    this.getProfile(name);
    this._userService.getAvatar(name)
    .subscribe(data =>{
      this.tmp = data.data.avatar;
      this.nameImg = 'data:image/png;base64,' + data.data.avatar;
    });
  }

 profile: any = {id: null, username: null, email: null}

  getProfile(username: string): void{
    this._userService.getUserOnlineList()
    .subscribe(data =>{
      for (var i = 0; i < data.data.length; i++) {
        if(data.data[i].username == username){
          this.profile.id = data.data[i].iD;
          this.profile.username = data.data[i].username;
          this.profile.email = data.data[i].email;
        }
      }
    });
  }

  scroll(el) {
    el.scrollIntoView();
  }

  changeFlag(){
    this.flag = true;
    this.isSeeMore = true;
    this.count = 5;
  }

  getUserOnlines() {
    this._userService.getUserOnlineList()
      .subscribe(data => {
        this.useronlines = [];
        for (let i = 0; i < data.data.length; i++) {
          if (data.data[i].username != sessionStorage.getItem('username'))
            this.useronlines.push({username: data.data[i].username, online: data.data[i].online});
        }
      })
  }

  editProfileClick(): void{
    this.isEditProfile = true;
  }

  cancelEditClick(): void{
    this.isEditProfile = false;
    this.intUpdateAvatar = 0;
  }

  updateClick(e) {

    if(this.pathImg == ''){
      this.intUpdateAvatar = -1;
    }
    else{
      this._userService.updateAvatar(this.loginedUser, this.pathImg[0].name)
      .subscribe(data =>{

        console.log(data);
        this.intUpdateAvatar = 1;
      });
      this.isEditProfile = false;
    }
      
    }

  getCurrentUser() {
    this._userService.getUserOnlineList()
      .subscribe(data => {
        for (let i = 0; i < data.data.length; i++) {
          if (data.data[i].username == sessionStorage.getItem('username')) {
            this.currentUser.id = data.data[i].iD;
            this.currentUser.email = data.data[i].email;
            this.currentUser.username = data.data[i].username;
            break;
          }
          console.log(this.currentUser);
        }
      });
  }

  sendMessage() {
    this._chatService.sendMessage(this.message, this.loginedUser);
    this.message = null;
  }

  viewProfileUser() {
    console.log("Profile user");
  }

  searchFriend() {
    alert("enter key ok");
    console.log(sessionStorage.getItem('username'));
  }

  loadOldMessages(e, selUser: string){
    this.flag = false;
    this._chatService.getOldMessage(this.loginedUser, selUser)
    .subscribe(oldMsg => {
      for (var i = 0; i < oldMsg.data.length; i++) {
        oldMsg.data[i].cr_date = this.convertDate(oldMsg.data[i].cr_date);
      }
      this.oldMessages = [];
      for (var i = 0; i < oldMsg.data.length; i++) {
        this.oldMessages.push({id: oldMsg.data[i].id, time: oldMsg.data[i].cr_date, text: oldMsg.data[i].text, sender: oldMsg.data[i].sender})
      }
      for (var i = 0; i < this.oldMessages.length - 1; i++) {
          for (var j = i; j < this.oldMessages.length; j++) {
            if(this.oldMessages[i].id > this.oldMessages[j].id){
              let tmpMsg = this.oldMessages[i];
              this.oldMessages[i] = this.oldMessages[j];
              this.oldMessages[j] = tmpMsg;
            }
          }
      }
      this.tmpOldMsgs = this.oldMessages.slice(0, this.count);
      //$(".content-oldMsg").scrollTop( this.tmpOldMsgs.length * 100);
    });
  }

  removeContact(item: string): void{
    let index: number = this.contactedusers.indexOf(item);
    if(index != -1){
      this.contactedusers.splice(index, 1);
    }
  }


  more(): void{
    this.count += this.oldMessages.length;
      this.tmpOldMsgs = this.oldMessages.slice(0, this.count);
      this.isSeeMore = false;
  }

  getMessages():void{
    this._chatService.getMessages(this.loginedUser, this.selectUser.selectUsername)
      .subscribe(msg => {
        for (var i = 0; i < msg.data.length; i++) {
          msg.data[i].cr_date = this.convertDate(msg.data[i].cr_date);
        }
        this.messages = [];
        for (var i = 0; i < msg.data.length; i++) {
          this.messages.push({id: msg.data[i].id, time: msg.data[i].cr_date, text: msg.data[i].text, sender: msg.data[i].sender}); 
        }
        for (var i = 0; i < this.messages.length - 1; i++) {
          for (var j = i; j < this.messages.length; j++) {
            if(this.messages[i].id > this.messages[j].id){
              let tmpMsg = this.messages[i];
              this.messages[i] = this.messages[j];
              this.messages[j] = tmpMsg;
            }
          }
        }
        $(".messages-view").scrollTop( this.messages.length * 100);
      
      });
      console.log(this.messages);
  }

  convertDate(timeStamp) {
    let d: any;
    d = new Date(timeStamp);
    return "sent at " + d;
  }

  chat(e, user) {
     setInterval(() =>{
      this.getMessages()
    }, 1100);
    console.log(user);
    this._userService.getUserOnlineList()
      .subscribe(data => {
        for (let i = 0; i < data.data.length; i++) {
          if (user == data.data[i].username) {
            this.selectUser.selectUserid = data.data[i].iD;
            this.selectUser.selectUserEmail = data.data[i].email;
            this.selectUser.selectUsername = data.data[i].username;
            break;
          }
        }
        this._chatService.createNewRoom(this.selectUser.selectUsername);

        this._chatService.getRoom()
          .subscribe(room => {
            this._chatService.saveRoom(room)
              .subscribe(data => {
                console.log(data);
              })
          });
          this.getMessages();
          this.nameFriendChatting = '   ' + this.selectUser.selectUsername;
      });
  }

  getRoom() {
    this._chatService.getRoom()
      .subscribe(room => {
        this._chatService.saveRoom(room)
          .subscribe(data => {
            console.log(data);
          })
      });
  }

  viewHistory(): void{
    this.isOpenHistory = true;
    this._chatService.getContactUser(this.loginedUser)
    .subscribe(data => {
      this.contactedusers = [];
      for (var i = 0; i < data.data.length; i++) {
        this.contactedusers.push(data.data[i]);
      }
    });
  }

  closeHistory(): void{
    this.isOpenHistory = false;
  }

  logout() {
    let userLogout = { username: sessionStorage.getItem('username') }
    this._userService.logoutfn(userLogout)
      .subscribe(data => {
        if (data.returnCode == 1) {
          this._router.navigate(['login']);
          sessionStorage.removeItem('username');
        }
        else {
          console.log(-1);
        }
      });
  }

  
}
