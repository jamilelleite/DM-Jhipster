import { IRestorateur } from 'app/shared/model/restorateur.model';
import { ILivreurs } from 'app/shared/model/livreurs.model';
import { IClient } from 'app/shared/model/client.model';
import { ICooperativeLocal } from 'app/shared/model/cooperative-local.model';
import { IConseil } from 'app/shared/model/conseil.model';

export interface ISocietaire {
  id?: number;
  nom?: string;
  directeur?: string | null;
  restorateurs?: IRestorateur[] | null;
  livreurs?: ILivreurs[] | null;
  clients?: IClient[] | null;
  coopname?: ICooperativeLocal;
  consname?: IConseil;
}

export const defaultValue: Readonly<ISocietaire> = {};
