import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './panier.reducer';

export const PanierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const panierEntity = useAppSelector(state => state.panier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="panierDetailsHeading">
          <Translate contentKey="coopcycleApp.panier.detail.title">Panier</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{panierEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="coopcycleApp.panier.name">Name</Translate>
            </span>
          </dt>
          <dd>{panierEntity.name}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="coopcycleApp.panier.price">Price</Translate>
            </span>
          </dt>
          <dd>{panierEntity.price}</dd>
          <dt>
            <Translate contentKey="coopcycleApp.panier.restname">Restname</Translate>
          </dt>
          <dd>{panierEntity.restname ? panierEntity.restname.id : ''}</dd>
          <dt>
            <Translate contentKey="coopcycleApp.panier.cliname">Cliname</Translate>
          </dt>
          <dd>{panierEntity.cliname ? panierEntity.cliname.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/panier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/panier/${panierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PanierDetail;
