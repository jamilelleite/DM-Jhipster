import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICooperativeNational } from 'app/shared/model/cooperative-national.model';
import { getEntities as getCooperativeNationals } from 'app/entities/cooperative-national/cooperative-national.reducer';
import { ICooperativeLocal } from 'app/shared/model/cooperative-local.model';
import { getEntity, updateEntity, createEntity, reset } from './cooperative-local.reducer';

export const CooperativeLocalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cooperativeNationals = useAppSelector(state => state.cooperativeNational.entities);
  const cooperativeLocalEntity = useAppSelector(state => state.cooperativeLocal.entity);
  const loading = useAppSelector(state => state.cooperativeLocal.loading);
  const updating = useAppSelector(state => state.cooperativeLocal.updating);
  const updateSuccess = useAppSelector(state => state.cooperativeLocal.updateSuccess);

  const handleClose = () => {
    navigate('/cooperative-local');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCooperativeNationals({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cooperativeLocalEntity,
      ...values,
      coopNaname: cooperativeNationals.find(it => it.id.toString() === values.coopNaname.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cooperativeLocalEntity,
          coopNaname: cooperativeLocalEntity?.coopNaname?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="coopcycleApp.cooperativeLocal.home.createOrEditLabel" data-cy="CooperativeLocalCreateUpdateHeading">
            <Translate contentKey="coopcycleApp.cooperativeLocal.home.createOrEditLabel">Create or edit a CooperativeLocal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="cooperative-local-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('coopcycleApp.cooperativeLocal.name')}
                id="cooperative-local-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('coopcycleApp.cooperativeLocal.zone')}
                id="cooperative-local-zone"
                name="zone"
                data-cy="zone"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="cooperative-local-coopNaname"
                name="coopNaname"
                data-cy="coopNaname"
                label={translate('coopcycleApp.cooperativeLocal.coopNaname')}
                type="select"
                required
              >
                <option value="" key="0" />
                {cooperativeNationals
                  ? cooperativeNationals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cooperative-local" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CooperativeLocalUpdate;
