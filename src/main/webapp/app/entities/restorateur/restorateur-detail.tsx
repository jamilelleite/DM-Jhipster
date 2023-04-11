import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './restorateur.reducer';

export const RestorateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const restorateurEntity = useAppSelector(state => state.restorateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="restorateurDetailsHeading">
          <Translate contentKey="coopcycleApp.restorateur.detail.title">Restorateur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{restorateurEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="coopcycleApp.restorateur.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{restorateurEntity.nom}</dd>
          <dt>
            <span id="theme">
              <Translate contentKey="coopcycleApp.restorateur.theme">Theme</Translate>
            </span>
          </dt>
          <dd>{restorateurEntity.theme}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="coopcycleApp.restorateur.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{restorateurEntity.zone}</dd>
          <dt>
            <span id="options">
              <Translate contentKey="coopcycleApp.restorateur.options">Options</Translate>
            </span>
          </dt>
          <dd>{restorateurEntity.options}</dd>
          <dt>
            <Translate contentKey="coopcycleApp.restorateur.listname">Listname</Translate>
          </dt>
          <dd>{restorateurEntity.listname ? restorateurEntity.listname.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/restorateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/restorateur/${restorateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RestorateurDetail;
