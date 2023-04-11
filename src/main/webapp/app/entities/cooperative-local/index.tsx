import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CooperativeLocal from './cooperative-local';
import CooperativeLocalDetail from './cooperative-local-detail';
import CooperativeLocalUpdate from './cooperative-local-update';
import CooperativeLocalDeleteDialog from './cooperative-local-delete-dialog';

const CooperativeLocalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CooperativeLocal />} />
    <Route path="new" element={<CooperativeLocalUpdate />} />
    <Route path=":id">
      <Route index element={<CooperativeLocalDetail />} />
      <Route path="edit" element={<CooperativeLocalUpdate />} />
      <Route path="delete" element={<CooperativeLocalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CooperativeLocalRoutes;
