import client from 'app/entities/client/client.reducer';
import restorateur from 'app/entities/restorateur/restorateur.reducer';
import livreurs from 'app/entities/livreurs/livreurs.reducer';
import societaire from 'app/entities/societaire/societaire.reducer';
import conseil from 'app/entities/conseil/conseil.reducer';
import panier from 'app/entities/panier/panier.reducer';
import cooperativeNational from 'app/entities/cooperative-national/cooperative-national.reducer';
import cooperativeLocal from 'app/entities/cooperative-local/cooperative-local.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  client,
  restorateur,
  livreurs,
  societaire,
  conseil,
  panier,
  cooperativeNational,
  cooperativeLocal,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
