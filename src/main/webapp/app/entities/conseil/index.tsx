import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Conseil from './conseil';
import ConseilDetail from './conseil-detail';
import ConseilUpdate from './conseil-update';
import ConseilDeleteDialog from './conseil-delete-dialog';

const ConseilRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Conseil />} />
    <Route path="new" element={<ConseilUpdate />} />
    <Route path=":id">
      <Route index element={<ConseilDetail />} />
      <Route path="edit" element={<ConseilUpdate />} />
      <Route path="delete" element={<ConseilDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConseilRoutes;
