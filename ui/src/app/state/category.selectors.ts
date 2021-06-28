import {createFeatureSelector, createSelector} from "@ngrx/store";
import {AppState} from "./app.state";
import {Category} from "../models/category.model";
import {Item} from "../models/item.model";

export const selectCategories = createSelector(
  (state: AppState) => {
    // console.log("s1: ", state);
    return state.categories
  },
  (categories: Array<Category>) => categories
)

//TODO: Consider loading pure container and join items on frontend

// export const selectCategoriesState =
//   createFeatureSelector<AppState, ReadonlyArray<Category[]>>("categories");

// export const selectXxx = createSelector(
//   selectItems,
//   selectCategoriesState,
//   (items: Array<Item>, categories: Array<Category>) => {
//     return categories.map(c => {
//       c.items = c.items.map(id => items.find(item => item._id == id) )
//       return c;
//     })
//   }
// )
