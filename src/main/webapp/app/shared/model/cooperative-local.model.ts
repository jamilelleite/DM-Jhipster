import { ICooperativeNational } from 'app/shared/model/cooperative-national.model';
import { ISocietaire } from 'app/shared/model/societaire.model';

export interface ICooperativeLocal {
  id?: number;
  name?: string;
  zone?: string;
  coopNaname?: ICooperativeNational;
  socnames?: ISocietaire[];
}

export const defaultValue: Readonly<ICooperativeLocal> = {};
