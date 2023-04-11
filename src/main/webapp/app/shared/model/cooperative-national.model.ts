import { ICooperativeLocal } from 'app/shared/model/cooperative-local.model';

export interface ICooperativeNational {
  id?: number;
  name?: string;
  fournisseur?: string;
  coopLos?: ICooperativeLocal[];
}

export const defaultValue: Readonly<ICooperativeNational> = {};
