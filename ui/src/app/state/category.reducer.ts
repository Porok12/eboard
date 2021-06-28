import {createReducer, on} from "@ngrx/store";
import {
  addCategory,
  addItem,
  deleteCategory,
  deleteItem,
  editCategory,
  editItem,
  getCategories
} from "./category.actions";
import {Category} from "../models/category.model";
import cloneDeep from "lodash.clonedeep";

export const initialState: Array<Category> = [
  {
    title: "title",
    items: [],
    description: "Desc"
  }
]

const _reducer = createReducer(
  initialState,
  on(addItem, (state, { category, item }) => state.map(c => {
    if (c._id == category._id) {
      const copy = cloneDeep(c);
      copy.items = copy.items.concat(item);
      return copy;
    }
    return c;
  })),
  on(editItem, (state, { item }) => state.map(c => {
    const copy = cloneDeep(c);
    copy.items = copy.items.map(i => i._id != item._id ? i : item)
    return copy;
  })),
  on(deleteItem, (state, { item }) => state.map(c => {
    const copy = cloneDeep(c);
    copy.items = c.items.filter(i => i._id != item._id);
    return copy;
  })),

  on(getCategories, (state, { categories }) => [...categories]),
  on(addCategory, (state, { category }) =>
    state.concat(category)),
  on(editCategory, (state, { category }) =>
    state.map(c => c._id.$oid != category._id.$oid ? c : category)),
  on(deleteCategory, (state, { category }) =>
    state.filter(c => c._id != category._id))
)

export function categoryReducer(state, action) {
  return _reducer(state, action);
}
