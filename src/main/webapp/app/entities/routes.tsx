import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Client from './client';
import Restorateur from './restorateur';
import Livreurs from './livreurs';
import Societaire from './societaire';
import Conseil from './conseil';
import Panier from './panier';
import CooperativeNational from './cooperative-national';
import CooperativeLocal from './cooperative-local';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="client/*" element={<Client />} />
        <Route path="restorateur/*" element={<Restorateur />} />
        <Route path="livreurs/*" element={<Livreurs />} />
        <Route path="societaire/*" element={<Societaire />} />
        <Route path="conseil/*" element={<Conseil />} />
        <Route path="panier/*" element={<Panier />} />
        <Route path="cooperative-national/*" element={<CooperativeNational />} />
        <Route path="cooperative-local/*" element={<CooperativeLocal />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
