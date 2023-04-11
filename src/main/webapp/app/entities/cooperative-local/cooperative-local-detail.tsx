import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cooperative-local.reducer';

export const CooperativeLocalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cooperativeLocalEntity = useAppSelector(state => state.cooperativeLocal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cooperativeLocalDetailsHeading">
          <Translate contentKey="coopcycleApp.cooperativeLocal.detail.title">CooperativeLocal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cooperativeLocalEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="coopcycleApp.cooperativeLocal.name">Name</Translate>
            </span>
          </dt>
          <dd>{cooperativeLocalEntity.name}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="coopcycleApp.cooperativeLocal.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{cooperativeLocalEntity.zone}</dd>
          <dt>
            <Translate contentKey="coopcycleApp.cooperativeLocal.coopNaname">Coop Naname</Translate>
          </dt>
          <dd>{cooperativeLocalEntity.coopNaname ? cooperativeLocalEntity.coopNaname.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cooperative-local" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cooperative-local/${cooperativeLocalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CooperativeLocalDetail;
