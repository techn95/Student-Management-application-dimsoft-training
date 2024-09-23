import { Component, computed, Input, Signal, signal } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterLink, RouterLinkActive } from '@angular/router';


@Component({
  selector: 'app-sidenav',
  standalone: true,
  imports: [MatSidenavModule, RouterLink, RouterLinkActive],
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.scss'
})
export class SidenavComponent {
}
