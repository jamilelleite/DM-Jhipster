import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cooperative-national.reducer';

export const CooperativeNationalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cooperativeNationalEntity = useAppSelector(state => state.cooperativeNational.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cooperativeNationalDetailsHeading">
          <Translate contentKey="coopcycleApp.cooperativeNational.detail.title">CooperativeNational</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cooperativeNationalEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="coopcycleApp.cooperativeNational.name">Name</Translate>
            </span>
          </dt>
          <dd>{cooperativeNationalEntity.name}</dd>
          <dt>
            <span id="fournisseur">
              <Translate contentKey="coopcycleApp.cooperativeNational.fournisseur">Fournisseur</Translate>
            </span>
          </dt>
          <dd>{cooperativeNationalEntity.fournisseur}</dd>
        </dl>
        <Button tag={Link} to="/cooperative-national" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cooperative-national/${cooperativeNationalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CooperativeNationalDetail;
