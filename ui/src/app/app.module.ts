import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon"
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";
import {DragDropModule} from "@angular/cdk/drag-drop";
import { AppRoutingModule } from './app-routing.module';
import { AboutComponent } from './components/about/about.component';
import { EboardComponent } from './components/eboard/eboard.component';
import { ClickableDirective } from './directives/clickable.directive';
import { ItemComponent } from './components/item/item.component';
import { CategoryComponent } from './components/category/category.component';

import {StoreModule} from "@ngrx/store";
import {categoryReducer} from "./state/category.reducer";

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    EboardComponent,
    ClickableDirective,
    ItemComponent,
    CategoryComponent,
  ],
  imports: [
    StoreModule.forRoot({ categories: categoryReducer }),
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatCheckboxModule,
    FormsModule,
    FlexLayoutModule,
    AppRoutingModule,
    DragDropModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
