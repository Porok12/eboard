import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Item} from "../../models/item.model";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {
  @Input() item: Item;
  @Output() deleteItemEvent = new EventEmitter();
  @Output() editItemEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  deleteItem(item: Item) {
    this.deleteItemEvent.emit(item);
  }

  editItem(item: Item) {
    this.editItemEvent.emit(item);
  }
}
