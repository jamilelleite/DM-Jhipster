import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CooperativeNational from './cooperative-national';
import CooperativeNationalDetail from './cooperative-national-detail';
import CooperativeNationalUpdate from './cooperative-national-update';
import CooperativeNationalDeleteDialog from './cooperative-national-delete-dialog';

const CooperativeNationalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CooperativeNational />} />
    <Route path="new" element={<CooperativeNationalUpdate />} />
    <Route path=":id">
      <Route index element={<CooperativeNationalDetail />} />
      <Route path="edit" element={<CooperativeNationalUpdate />} />
      <Route path="delete" element={<CooperativeNationalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CooperativeNationalRoutes;
