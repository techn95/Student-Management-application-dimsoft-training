import { Component, computed, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuComponent } from '../../components/menu/menu.component';
import { SidenavComponent } from '../../components/sidenav/sidenav.component';

@Component({
  selector: 'app-default-layout',
  standalone: true,
  imports: [RouterOutlet, MenuComponent, SidenavComponent],
  templateUrl: './default-layout.component.html',
  styleUrl: './default-layout.component.scss'
})
export class DefaultLayoutComponent {

  items = ['item1', 'item2', 'item3', 'item4'];

  addItem(newItem: string) {
    this.items.push(newItem);
    console.log("Added")
    console.log(this.items)
  }

  tooglecustom(event: boolean){
    const bodyclass = document.body.classList;
    bodyclass.toggle("toggle-sidebar");
    console.log(event)
  }
}
