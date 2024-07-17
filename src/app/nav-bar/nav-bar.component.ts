import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
@Component({
  selector: 'app-menue-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  menuItems: MenuItem[] = [];



  ngOnInit(): void {
    this.updateMenuItems();
  }

  private updateMenuItems(): void {
    this.menuItems = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: ['/home'],
        items: [{ label: 'View Archive', icon: 'pi pi-search', routerLink: ['/home'] }]
      },
      { label: 'Sign in', icon: 'pi pi-sign-in', routerLink: ['/sign-in'] },
      { label: 'Sign Up', icon: 'pi pi-user-plus', routerLink: ['/sign-up']},
      {
        label: 'Sign out',
        icon: 'pi pi-sign-out',
        routerLink: ['/sign-out'],

      }
    ];
  }
}
