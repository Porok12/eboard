import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {Category} from "../../models/category.model";
import {Item} from "../../models/item.model";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  @Input() categories: Category[];
  @Input() category: Category;
  @Input() listIndex: number;

  @Output() addItemEvent = new EventEmitter();
  @Output() deleteItemEvent = new EventEmitter();
  @Output() editItemEvent = new EventEmitter();

  @Output() createCategoryEvent = new EventEmitter();
  @Output() editCategoryEvent = new EventEmitter();
  @Output() deleteCategoryEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  addItem(category: Category) {
    const item: Item = {
      description: "",
      name: Math.random().toString(36).split('.')[1]
    }
    this.addItemEvent.emit({category, item});
  }

  deleteItem(item: Item) {
    this.deleteItemEvent.emit(item);
  }

  editItem(item: Item) {
    this.editItemEvent.emit(item);
  }

  onDrop(event: CdkDragDrop<Item[]>) {
    // console.log(event.container.id);
    this.editCategoryEvent.emit(event);

    if (event.previousContainer === event.container) {
      // API

      // moveItemInArray(
      //   event.container.data,
      //   event.previousIndex,
      //   event.currentIndex
      // );
    } else {
      // API

      // transferArrayItem(
      //   event.previousContainer.data,
      //   event.container.data,
      //   event.previousIndex,
      //   event.currentIndex
      // );
    }
  }

  getOthers(category: Category) {
    const prefix = 'card-'; //'cdk-drop-list-';
    const myId = prefix + this.categories.findIndex(c => c.title === category.title);
    const results = this.categories
        .map((v, i) => prefix + i)
        .filter(v => v !== myId);
    // console.log(results);
    return results;
  }

  deleteCategory(category: Category): void {
    this.deleteCategoryEvent.emit(category);
  }
}
