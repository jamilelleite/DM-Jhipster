import { ISocietaire } from 'app/shared/model/societaire.model';

export interface ILivreurs {
  id?: number;
  nom?: string;
  listname?: ISocietaire;
}

export const defaultValue: Readonly<ILivreurs> = {};
