import { IRestorateur } from 'app/shared/model/restorateur.model';
import { IClient } from 'app/shared/model/client.model';

export interface IPanier {
  id?: number;
  name?: string;
  price?: number;
  restname?: IRestorateur;
  cliname?: IClient;
}

export const defaultValue: Readonly<IPanier> = {};
