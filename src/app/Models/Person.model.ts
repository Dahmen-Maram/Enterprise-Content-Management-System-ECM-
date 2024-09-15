export class Person {
  name: string;
  lastName: string;
  gender: string;
  address: string;
  phone: string;
  email: string;
  birthDate: Date;
  password: string;

  constructor() {
    this.name = '';
    this.lastName = '';
    this.gender = '';
    this.address = '';
    this.phone ='';
    this.email = '';
    this.password='';
    this.birthDate = new Date();
  }
}
