import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './livreurs.reducer';

export const LivreursDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const livreursEntity = useAppSelector(state => state.livreurs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="livreursDetailsHeading">
          <Translate contentKey="coopcycleApp.livreurs.detail.title">Livreurs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{livreursEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="coopcycleApp.livreurs.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{livreursEntity.nom}</dd>
          <dt>
            <Translate contentKey="coopcycleApp.livreurs.listname">Listname</Translate>
          </dt>
          <dd>{livreursEntity.listname ? livreursEntity.listname.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/livreurs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/livreurs/${livreursEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LivreursDetail;
