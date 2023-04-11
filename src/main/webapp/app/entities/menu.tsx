import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/restorateur">
        <Translate contentKey="global.menu.entities.restorateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/livreurs">
        <Translate contentKey="global.menu.entities.livreurs" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/societaire">
        <Translate contentKey="global.menu.entities.societaire" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/conseil">
        <Translate contentKey="global.menu.entities.conseil" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/panier">
        <Translate contentKey="global.menu.entities.panier" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cooperative-national">
        <Translate contentKey="global.menu.entities.cooperativeNational" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cooperative-local">
        <Translate contentKey="global.menu.entities.cooperativeLocal" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
