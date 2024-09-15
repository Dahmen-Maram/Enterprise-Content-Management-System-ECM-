import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from 'src/app/sign-up/sign-up.component';
import {FileArchiveComponent} from "./filearchive/filearchive.component";




const routes: Routes = [
  { path: 'home', component: HomeComponent /* ,canActivate: [AuthService]*/ },
  { path: 'signin', component: SignInComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'filearchive', component:FileArchiveComponent  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
