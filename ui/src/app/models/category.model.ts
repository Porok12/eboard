import {Item} from "./item.model";

export interface Category {
  _id?: string;
  title: string;
  description: string;
  items: Item[];
}
