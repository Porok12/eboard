import {Component, Inject, OnInit} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {ItemService} from "../../services/item.service";
import {select, Store} from "@ngrx/store";
import {
  addCategory,
  addItem,
  deleteCategory,
  deleteItem,
  editCategory,
  getCategories
} from "../../state/category.actions";
import {Observable} from "rxjs";
import {Category} from "../../models/category.model";
import {Item} from "../../models/item.model";
// import {selectCategories} from "../../state/category.selectors";
import {AppState} from "../../state/app.state";
import {selectCategories} from "../../state/category.selectors";
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import cloneDeep from "lodash.clonedeep";

@Component({
  selector: 'app-eboard',
  templateUrl: './eboard.component.html',
  styleUrls: ['./eboard.component.scss']
})
export class EboardComponent implements OnInit {
  categories$: Observable<Category[]>;
  private tCategories: Category[];

  constructor(
    private categoryService: CategoryService,
    private itemService: ItemService,
    private store: Store<AppState>
  ) {
    this.categories$ = store.pipe(select(selectCategories));
    this.categories$.subscribe(c => this.tCategories = c);
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getAll().subscribe((categories: any) => {
        // console.log('Service: ', categories);
        this.store.dispatch(getCategories({ categories }));
    })
  }

  createCategory() {
    const category: Category = {
      description: "",
      items: [],
      title: Math.random().toString(36).split('.')[1]
    }
    this.categoryService.create(category).subscribe((category: Category) => {
      // console.log('Service: ', category);
      this.store.dispatch(addCategory({ category }));
    });
  }

  editCategory(event: CdkDragDrop<Item[]>, category: Category) {
    // this.store.dispatch(editEventCategory({ event }));

    if (event.previousContainer === event.container) {
      const d = event.container.data;
      const p = event.previousIndex;
      const c = event.currentIndex;
      // const items = [d[p], d[c]] = [d[c], d[p]]
      const items = d.map((v, i, a) => {
        if (p == i) return a[c];
        else if (c == i) return a[p]
        else return v;
      })
      const copy = cloneDeep(category);
      copy.items = items;
      this.store.dispatch(editCategory({ category: copy }));

      // moveItemInArray(
      //   event.container.data,
      //   event.previousIndex,
      //   event.currentIndex
      // );
    } else {
      const pd = event.previousContainer.data;
      const cd = event.container.data;
      const p = event.previousIndex;
      const c = event.currentIndex;

      console.log(event.previousContainer.id, event.container.id)

      const tArr = event.previousContainer.id.split('-');
      const cArr = event.container.id.split('-');
      const pId = parseInt(tArr[tArr.length - 1]);
      const cId = parseInt(cArr[cArr.length - 1]);
      // console.log(pId, cId);

      const c1 = cloneDeep(this.tCategories[pId]);
      const c2 = cloneDeep(this.tCategories[cId]);
      // console.log(c1, c2);

      c2.items.splice(c, 0, c1.items[p]);
      c1.items = c1.items.filter((_, i) => i != p);

      this.store.dispatch(editCategory({ category: c1 }));
      this.store.dispatch(editCategory({ category: c2 }));

      // transferArrayItem(
      //   event.previousContainer.data,
      //   event.container.data,
      //   event.previousIndex,
      //   event.currentIndex
      // );
    }
  }

  deleteCategory(category: Category) {
    this.categoryService.delete(category).subscribe((categoryId: string) => {
      // console.log('Service: ', categoryId);
      this.store.dispatch(deleteCategory({ category }))
    });
  }

  addItem({category, item}) {
    this.itemService.createWithin(category, item).subscribe((item: Item) => {
      // console.log('Service: ', item);
      this.store.dispatch(addItem({ category, item }))
    });
  }

  deleteItem(item: Item) {
    this.itemService.delete(item).subscribe(itemId => {
      // console.log('Service: ', itemId);
      this.store.dispatch(deleteItem({ item }))
    })
  }
}
