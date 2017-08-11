import { Injectable } from '@angular/core';
import { Subject } from "rxjs/Subject";
import { Observable } from "rxjs/Observable";
import * as io from "socket.io-client";
import { Http, Response, Headers, RequestOptions, RequestOptionsArgs } from '@angular/http';

@Injectable()
export class ChatService {

   private BASE_URL = 'http://10.0.0.206:8000';
   private socket: any;

   private url;
  // private URL_CONNECTION = 'http://10.0.0.206:';
  // private newSocket: any;

  constructor(private _http: Http) { }

  // createConntion(port: any): void {
  //   let observable = new Observable((observer: any) => {
  //     this.newSocket = io(this.URL_CONNECTION + port);
  //   });
  //}

  // sendMessage(message: string, username: string) {
  //   this.socket.emit('add-message', message, username);
  // }

  // getMessage() {
  //   let observable = new Observable((observer: any) => {
  //     this.socket = io(this.BASE_URL);
  //     this.socket.on('message', (data: any) => {
  //       observer.next(data);
  //     });
  //     return () => {
  //       this.socket.disconnect();
  //     };
  //   })
  //   return observable;
  // }

  getUsername() {
    return sessionStorage.getItem('username');
  }

  setUsername(username: string) {
    console.log('username set: ' + username);
    sessionStorage.setItem('username', username);
  }

  // // send username and id of current user to Nodejs
  // public sendInfoUserConnected(currentUser: any) {
  //   //let data = {idUserConnected: idUserConnected, usernameConnected: usernameConnected };
  //   let headers = new Headers({ 'Content-Type': 'application/json' });
  //   let options = new RequestOptions({ headers: headers });

  //   return this._http.post('http://10.0.0.206:8000/infouserconnected', JSON.stringify(currentUser), options)
  //     .map((res: Response) => {
  //       let response = res.json();
  //       return response;
  //     });
  // }

  // //recieve socket id from nodejs
  // public recieveSocketid() {
  //   return this._http.get('http://10.0.0.206:8000/socketId')
  //     .map((data: Response) => {
  //       let response = data.json();
  //       return response;
  //     });
  // }

  // //require server create a PORT
  // public requireCreatePort(dataUser: any) {
  //   let headers = new Headers({ 'Content-Type': 'application/json' });
  //   let options = new RequestOptions({ headers: headers });
  //   return this._http.post('http://10.0.0.206:8000/requireCreatePort', dataUser, options)
  //     .map((res: Response) => {
  //       let response = res.json();
  //       return response;
  //     });
  // }

  //connect to Nodejs server
  connectedServer(username: string): void{
    this.socket = io.connect(this.BASE_URL);
    this.socket.emit('new-user-connected', username);
  }

  // create new room for 2 users
  createNewRoom(toUsername: string): void{
    this.socket.emit('new-room', toUsername);
  }

  sendMessage(message: string, sender: string): void{
    this.socket.emit('private-chat', message, sender);
  }

  getMessage(){
    let observable = new Observable((observer: any) => {
      this.socket.on('message', (data: any) => {
        observer.next(data);
      });
      return () => {
        this.socket.disconnect();
      };
    })
    return observable;
  }

  getRoom(){
    let observable = new Observable((observer: any) => {
      this.socket.on('room', (data: any) => {
        observer.next(data);
      });
      return () => {
        this.socket.disconnect();
      };
    })
    return observable;
  }

  //disconnect server
  discServer(): void{
    this.socket.disconnect();
    console.log('disc');
  }

  saveRoom(room: any){
    this.url = 'http://10.0.0.206:8080/cntc/rest/room/saveRoom';
    let headers = new Headers([ {'Content-Type': 'application/json'}, {'Accept' : 'application/json'}]);
    let options = new RequestOptions({ headers: headers });
    return this._http.post(this.url, room, options)
    .map((data: Response) =>{
      let response = data.json();
      return response;
    });
  }

  saveMessage(message: any){
     this.url = 'http://10.0.0.206:8080/cntc/rest/message/saveMessage';
    let headers = new Headers([ {'Content-Type': 'application/json'}, {'Accept' : 'application/json'}]);
    let options = new RequestOptions({ headers: headers });
    return this._http.post(this.url, message, options)
    .map((data: Response) =>{
      let response = data.json();
      return response;
    });
  }

  getMessages(fromUser: string, toUser: string){
    this.url = 'http://10.0.0.206:8080/cntc/rest/message/getMessages?fromUser=' + fromUser + '&toUser=' + toUser;
    return this._http.get(this.url)
      .map((data: Response) => {
        let response = data.json();
        return response;
      });
  }

  getContactUser(username: string){
    this.url = 'http://10.0.0.206:8080/cntc/rest/room/getContactUser?username=' + username;
    return this._http.get(this.url)
      .map((data: Response) => {
        let response = data.json();
        return response;
      });
  }

  getOldMessage(curUser: string, selUser: string){
    this.url = 'http://10.0.0.206:8080/cntc/rest/message/getOldMessages?curUser=' + curUser + '&selUser=' + selUser;
    return this._http.get(this.url)
      .map((data: Response) => {
        let response = data.json();
        return response;
      });
  }
}