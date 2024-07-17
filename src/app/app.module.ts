import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import {MenubarModule} from "primeng/menubar";
import {InputTextModule} from "primeng/inputtext";
import { SignInComponent } from './sign-in/sign-in.component';
import { SignOutComponent } from './sign-out/sign-out.component';
import { HomeComponent } from './home/home.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import {CheckboxModule} from "primeng/checkbox";
import {PaginatorModule} from "primeng/paginator";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CalendarModule} from "primeng/calendar";
import {PasswordModule} from "primeng/password";
import {PersonService} from "../services/person.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    SignInComponent,
    SignOutComponent,
    HomeComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    InputTextModule,
    CheckboxModule,
    PaginatorModule,
    BrowserAnimationsModule,
    CalendarModule,
    PasswordModule,
    HttpClientModule
  ],
  providers: [PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
