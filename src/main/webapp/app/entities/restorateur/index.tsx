import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Restorateur from './restorateur';
import RestorateurDetail from './restorateur-detail';
import RestorateurUpdate from './restorateur-update';
import RestorateurDeleteDialog from './restorateur-delete-dialog';

const RestorateurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Restorateur />} />
    <Route path="new" element={<RestorateurUpdate />} />
    <Route path=":id">
      <Route index element={<RestorateurDetail />} />
      <Route path="edit" element={<RestorateurUpdate />} />
      <Route path="delete" element={<RestorateurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RestorateurRoutes;
