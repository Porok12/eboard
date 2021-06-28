import { Component, OnInit } from '@angular/core';
// import {TodosService} from "../../services/todos.service";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  title = 'ui';
  newName: string;

  constructor(public todosService: any) { }

  ngOnInit(): void {
  }

  add() {
    return this.todosService.add({
      name: this.newName,
      isCompleted: false
    }).subscribe((data: {}) => {
      console.log(data);
    })
  }
}
