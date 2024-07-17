import { Component } from '@angular/core';
import {Person} from "../Models/Person.model";
import {PersonService} from "../../services/person.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  date : Date;
  savepassword:any;
  confirmedpassword:any;
  person=new Person();

  constructor(private personService:PersonService) {
  this.date=new Date();}
  submitform():void{
    this.person.birthDate=this.date;
    this.personService.addPerson(this.person).subscribe(response =>{
      alert('Person added successfully');
      this.restform();
    },
      error => {console.error('Error adding person', error);
  }
);
}
private restform(){
  this.person = new Person();
  }
}
