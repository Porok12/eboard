import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AboutComponent} from "./components/about/about.component";
import {EboardComponent} from "./components/eboard/eboard.component";

const routes: Routes = [
  { path: '', component: EboardComponent},
  { path: 'about', component: AboutComponent},
  { path: 'categories/:id', component: AboutComponent},
  { path: 'categories/:id/items/:id', component: AboutComponent}
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
