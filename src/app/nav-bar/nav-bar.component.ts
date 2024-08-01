import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import {AuthService} from "../../services/auth.service";
import {SignOutComponent} from "../sign-out/sign-out.component";
@Component({
  selector: 'app-menue-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  menuItems: MenuItem[] = [];

  constructor(private authService: AuthService) {}


  ngOnInit(): void {
    this.updateMenuItems();

    this.authService.isAuthenticated$.subscribe(isAuthenticated => {
      this.updateMenuItems();
    });
  }

  private updateMenuItems(): void {
    const isAuthenticated: boolean=this.authService.isAuthenticated;

    this.menuItems = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: ['/home'],
        items: [{ label: 'View Archive', icon: 'pi pi-search', routerLink: ['/home'] }]
      },
      { label: 'Sign in', icon: 'pi pi-sign-in', routerLink: ['/signin'], visible: !isAuthenticated },
      { label: 'Sign Up', icon: 'pi pi-user-plus', routerLink: ['/signup'], visible: !isAuthenticated },
      {
        label: 'Sign out',
        icon: 'pi pi-sign-out',
        routerLink: ['/sign-out'],
        visible: isAuthenticated,
        command:() => this.authService.logout()
      }
    ];
  }
}
