import { User } from "./user";

export interface Customer {
  username: string;
  cart: Product[];
  state: string;
  city: string;
  street: string;
}
