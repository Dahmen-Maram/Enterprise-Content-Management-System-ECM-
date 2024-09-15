import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {
  showSignIn = true;
  email: string = '';
  password: string = '';
  requiredEmailError: boolean = false;
  requiredPasswordError: boolean = false;
  signInError: string = '';


  constructor(private authService: AuthService , private router: Router) { }

  submitForm(): void {
    this.requiredEmailError = !this.email;
    this.requiredPasswordError = !this.password;
    this.signInError = ''; // Clear previous errors
    if (this.requiredEmailError || this.requiredPasswordError) {
      return;
    }

    this.authService.signIn(this.email, this.password)
      .subscribe(response => {
        if (response && response.message === 'login successful') {
          alert('Sign in successful');
          this.router.navigate(['/filearchive']);
          this.authService.login();
        } else {
          alert('Invalid email or password'); // Adjust based on API error handling
        }
      }, error => {
        console.error('Error signing in:', error);
        this.signInError = 'An error occurred. Please try again later.';
        // Handle error cases, such as network errors
      });
  }

  register():void{
    this.router.navigate(['/signup']);
  }
}
