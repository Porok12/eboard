import { createAction, props } from "@ngrx/store";
import {Item} from "../models/item.model";
import {Category} from "../models/category.model";

export const addItem = createAction('[Item List] Add Item',
  props<{ category: Category, item: Item }>());
export const editItem = createAction('[Item List] Edit Item',
  props<{ item: Item }>());
export const deleteItem = createAction('[Item List] Delete Item',
  props<{ item: Item }>());

export const getCategories = createAction('[Category List/API] Get Categories',
  props<{ categories: Array<Category> }>());
export const addCategory = createAction('[Category List] Add Category',
  props<{ category: Category }>());
export const editCategory = createAction('[Category List] Edit Category',
  props<{ category: Category }>());
export const deleteCategory = createAction('[Category List] Delete Category',
  props<{ category: Category }>());
