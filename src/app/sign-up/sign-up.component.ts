import { Component } from '@angular/core';
import {Person} from "../Models/Person.model";
import {PersonService} from "../../services/person.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  date : Date;
  savepassword:any;
  confirmedpassword:String='';
  person=new Person();
  RequiredfirstnameError:boolean=false;
  RequiredlastnameError:boolean=false;
  RequiredadressError:boolean=false;
  RequiredpasswordError:boolean=false;
  RequiredpasswordnameError:boolean=false;
  RequiredSamepasswordError:boolean=false;
  RequiredphoneNumberError:boolean=false;
  RequiredEmailError:boolean=false;
  RequiredDateOfBirthError:boolean=false;
  ageError:boolean=false;

  constructor(private personService:PersonService,private authService: AuthService, private router:Router) {
  this.date=new Date();}
  submitform():void{
    this.RequiredfirstnameError = !this.person.name;
    this.RequiredlastnameError = !this.person.lastName;
    this.RequiredphoneNumberError = !this.person.phone;
    this.RequiredadressError = !this.person.address;
    this.RequiredEmailError = !this.person.email;
    this.RequiredDateOfBirthError = !this.person.birthDate;
    this.RequiredpasswordError = !this.person.password;
    this.RequiredpasswordnameError = !this.confirmedpassword;
    this.RequiredSamepasswordError = this.person.password !== this.confirmedpassword;

    if (
      this.RequiredlastnameError ||
      this.RequiredlastnameError ||
      this.RequiredphoneNumberError ||
      this.RequiredadressError ||
      this.RequiredEmailError ||
      this.RequiredDateOfBirthError ||
      this.RequiredpasswordError ||
      this.RequiredpasswordnameError ||
      this.RequiredSamepasswordError
    ) {
      return;
    }

    this.person.birthDate=this.date;
    this.personService.addPerson(this.person).subscribe(response =>{
      alert('Person added successfully');
      this.authService.login();

      this.restform();
    },
      error => {console.error('Error adding person', error);
  }
);
}
private restform(){
  this.person = new Person();
  this.RequiredfirstnameError=false;
  this.RequiredlastnameError=false;
  this.RequiredadressError=false;
  this.RequiredpasswordError=false;
  this.RequiredpasswordnameError=false;
  this.RequiredSamepasswordError=false;
  this.RequiredphoneNumberError=false;
  this.RequiredEmailError=false;
  this.RequiredDateOfBirthError=false;
  this.ageError=false;
  }
  validateAge(): void {
    if (this.date) {
      const today = new Date();
      const birthDate = new Date(this.date);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();

      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      this.ageError = age < 18;
    }
  }
  validatepass(): void {
    this.RequiredpasswordError = !this.person.password;
    this.RequiredpasswordnameError = !this.confirmedpassword;
    this.RequiredSamepasswordError = this.person.password !== this.confirmedpassword;
  }
  navigateToPage()
{
  this.router.navigate(['/signin']);
}


}
