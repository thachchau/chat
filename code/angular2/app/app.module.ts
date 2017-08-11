import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';

import { RouterModule, Router } from "@angular/router";
import { routing } from "./app.routing";

import { UserService } from "./services/user-service.service";
import { HistoryComponent } from './home/history/history.component';
import { ChatService } from "./services/chat.service";

import { HttpModule } from "@angular/http";

import {
  DxCheckBoxModule,
  DxSelectBoxModule,
  DxNumberBoxModule,
  DxFormModule,
  DxButtonModule,
  DxTextBoxModule,
  DxValidationGroupModule,
  DxValidatorModule,
  DxListModule, DxTextAreaModule, DxNavBarModule, DxPopupModule, DxPopoverModule, DxFileUploaderModule
} from 'devextreme-angular';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    HistoryComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    routing,
    HttpModule,
    DxCheckBoxModule,
    DxSelectBoxModule,
    DxNumberBoxModule,
    DxFormModule,
    DxButtonModule,
    DxTextBoxModule,
    DxValidationGroupModule,
    DxValidatorModule,
    DxFormModule,
    DxListModule,
    DxTextAreaModule,
    DxNavBarModule,
    DxPopupModule,
    DxPopoverModule, DxFileUploaderModule, 

  ],
  providers: [
    UserService,
    ChatService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(router: Router) {
  }
}
