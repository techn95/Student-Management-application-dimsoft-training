import { Component, computed, EventEmitter, Input, Output, Signal, signal } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  @Output() newItemEvent2 = new EventEmitter<boolean>();

  tooglecustom(){
   this.newItemEvent2.emit(true);
  }

  // @Output() newItemEvent = new EventEmitter<string>();

  // addNewItem(value: string) {
  //   this.newItemEvent.emit(value);
  //   console.log(value)
  // }

  showResponsiveSearch(){
    const searchFormClass = document.querySelector(".search-bar")?.classList;
    if (searchFormClass)
      searchFormClass.toggle("search-bar-show")
  }
}
