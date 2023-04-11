import { IPanier } from 'app/shared/model/panier.model';
import { ISocietaire } from 'app/shared/model/societaire.model';

export interface IClient {
  id?: number;
  name?: string;
  address?: string | null;
  paniers?: IPanier[] | null;
  listname?: ISocietaire;
}

export const defaultValue: Readonly<IClient> = {};
