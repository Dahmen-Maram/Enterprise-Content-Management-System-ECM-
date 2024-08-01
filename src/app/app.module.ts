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
import {FileUploadModule} from "primeng/fileupload";
import {ToastModule} from "primeng/toast";
import { FileArchiveComponent } from './filearchive/filearchive.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import {FormsModule} from "@angular/forms";
import {StepsModule} from "primeng/steps";
import {CardModule} from "primeng/card";
import {MessageService} from "primeng/api";
import { TreeModule } from 'primeng/tree';
import {TreeTableModule} from "primeng/treetable";
import {AutoCompleteModule} from "primeng/autocomplete";
import {ToolbarModule} from "primeng/toolbar";
import {SplitButtonModule} from "primeng/splitbutton";
import {ConfirmDialogModule} from "primeng/confirmdialog";




@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    SignInComponent,
    SignOutComponent,
    HomeComponent,
    SignUpComponent,
    FileArchiveComponent
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
    HttpClientModule,
    ToastModule,
    FileUploadModule,
    RadioButtonModule,
    FormsModule,
    StepsModule,
    CardModule,
    TreeModule,
    TreeTableModule,
    AutoCompleteModule,
    ToolbarModule,
    SplitButtonModule,
    ConfirmDialogModule
  ],
  providers: [PersonService,MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
