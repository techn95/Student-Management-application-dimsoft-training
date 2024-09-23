import { Routes } from '@angular/router';
import { DefaultLayoutComponent } from './default-layout/default-layout/default-layout.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  {
    path: '',component: DefaultLayoutComponent, children:[
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadChildren: () => import('./views/dashboard/dashboard.module').then(m => m.DashboardModule) },
      { path: 'subject', loadChildren: () => import('./views/subject/subject.module').then(m => m.SubjectModule) },
      { path: 'student', loadChildren: () => import('./views/student/student.module').then(m => m.StudentModule) },
      { path: 'teacher', loadChildren: () => import('./views/teacher/teacher.module').then(m => m.TeacherModule) },
      { path: 'classroom', loadChildren: () => import('./views/classroom/classroom.module').then(m => m.ClassroomModule) }
    ]
  },
  {
    path: "login", component: LoginComponent
  }

];
