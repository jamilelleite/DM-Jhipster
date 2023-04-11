import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Livreurs from './livreurs';
import LivreursDetail from './livreurs-detail';
import LivreursUpdate from './livreurs-update';
import LivreursDeleteDialog from './livreurs-delete-dialog';

const LivreursRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Livreurs />} />
    <Route path="new" element={<LivreursUpdate />} />
    <Route path=":id">
      <Route index element={<LivreursDetail />} />
      <Route path="edit" element={<LivreursUpdate />} />
      <Route path="delete" element={<LivreursDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LivreursRoutes;
