import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICooperativeLocal } from 'app/shared/model/cooperative-local.model';
import { getEntities as getCooperativeLocals } from 'app/entities/cooperative-local/cooperative-local.reducer';
import { IConseil } from 'app/shared/model/conseil.model';
import { getEntities as getConseils } from 'app/entities/conseil/conseil.reducer';
import { ISocietaire } from 'app/shared/model/societaire.model';
import { getEntity, updateEntity, createEntity, reset } from './societaire.reducer';

export const SocietaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cooperativeLocals = useAppSelector(state => state.cooperativeLocal.entities);
  const conseils = useAppSelector(state => state.conseil.entities);
  const societaireEntity = useAppSelector(state => state.societaire.entity);
  const loading = useAppSelector(state => state.societaire.loading);
  const updating = useAppSelector(state => state.societaire.updating);
  const updateSuccess = useAppSelector(state => state.societaire.updateSuccess);

  const handleClose = () => {
    navigate('/societaire');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCooperativeLocals({}));
    dispatch(getConseils({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...societaireEntity,
      ...values,
      coopname: cooperativeLocals.find(it => it.id.toString() === values.coopname.toString()),
      consname: conseils.find(it => it.id.toString() === values.consname.toString()),
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
          ...societaireEntity,
          coopname: societaireEntity?.coopname?.id,
          consname: societaireEntity?.consname?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="coopcycleApp.societaire.home.createOrEditLabel" data-cy="SocietaireCreateUpdateHeading">
            <Translate contentKey="coopcycleApp.societaire.home.createOrEditLabel">Create or edit a Societaire</Translate>
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
                  id="societaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('coopcycleApp.societaire.nom')}
                id="societaire-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('coopcycleApp.societaire.directeur')}
                id="societaire-directeur"
                name="directeur"
                data-cy="directeur"
                type="text"
              />
              <ValidatedField
                id="societaire-coopname"
                name="coopname"
                data-cy="coopname"
                label={translate('coopcycleApp.societaire.coopname')}
                type="select"
                required
              >
                <option value="" key="0" />
                {cooperativeLocals
                  ? cooperativeLocals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="societaire-consname"
                name="consname"
                data-cy="consname"
                label={translate('coopcycleApp.societaire.consname')}
                type="select"
                required
              >
                <option value="" key="0" />
                {conseils
                  ? conseils.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/societaire" replace color="info">
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

export default SocietaireUpdate;
