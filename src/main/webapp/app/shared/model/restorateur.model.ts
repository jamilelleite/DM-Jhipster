import { IPanier } from 'app/shared/model/panier.model';
import { ISocietaire } from 'app/shared/model/societaire.model';

export interface IRestorateur {
  id?: number;
  nom?: string;
  theme?: string | null;
  zone?: string | null;
  options?: string | null;
  paniers?: IPanier[] | null;
  listname?: ISocietaire;
}

export const defaultValue: Readonly<IRestorateur> = {};
