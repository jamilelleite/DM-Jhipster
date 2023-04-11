import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISocietaire } from 'app/shared/model/societaire.model';
import { getEntities as getSocietaires } from 'app/entities/societaire/societaire.reducer';
import { IRestorateur } from 'app/shared/model/restorateur.model';
import { getEntity, updateEntity, createEntity, reset } from './restorateur.reducer';

export const RestorateurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const societaires = useAppSelector(state => state.societaire.entities);
  const restorateurEntity = useAppSelector(state => state.restorateur.entity);
  const loading = useAppSelector(state => state.restorateur.loading);
  const updating = useAppSelector(state => state.restorateur.updating);
  const updateSuccess = useAppSelector(state => state.restorateur.updateSuccess);

  const handleClose = () => {
    navigate('/restorateur');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getSocietaires({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...restorateurEntity,
      ...values,
      listname: societaires.find(it => it.id.toString() === values.listname.toString()),
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
          ...restorateurEntity,
          listname: restorateurEntity?.listname?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="coopcycleApp.restorateur.home.createOrEditLabel" data-cy="RestorateurCreateUpdateHeading">
            <Translate contentKey="coopcycleApp.restorateur.home.createOrEditLabel">Create or edit a Restorateur</Translate>
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
                  id="restorateur-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('coopcycleApp.restorateur.nom')}
                id="restorateur-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('coopcycleApp.restorateur.theme')}
                id="restorateur-theme"
                name="theme"
                data-cy="theme"
                type="text"
              />
              <ValidatedField
                label={translate('coopcycleApp.restorateur.zone')}
                id="restorateur-zone"
                name="zone"
                data-cy="zone"
                type="text"
              />
              <ValidatedField
                label={translate('coopcycleApp.restorateur.options')}
                id="restorateur-options"
                name="options"
                data-cy="options"
                type="text"
              />
              <ValidatedField
                id="restorateur-listname"
                name="listname"
                data-cy="listname"
                label={translate('coopcycleApp.restorateur.listname')}
                type="select"
                required
              >
                <option value="" key="0" />
                {societaires
                  ? societaires.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/restorateur" replace color="info">
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

export default RestorateurUpdate;
