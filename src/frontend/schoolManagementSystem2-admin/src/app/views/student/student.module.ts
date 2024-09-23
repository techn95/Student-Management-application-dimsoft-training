import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import {CreateOrUpdateComponent} from "./components/create-or-update/create-or-update.component";
import {IndexComponent} from "./components/index/index.component";


@NgModule({
  declarations: [
    CreateOrUpdateComponent,
    IndexComponent
  ],
  imports: [
    CommonModule,
    StudentRoutingModule
  ]
})
export class StudentModule { }
