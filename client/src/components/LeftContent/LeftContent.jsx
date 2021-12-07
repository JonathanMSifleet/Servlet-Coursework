import React, { useContext } from 'react';
import Context from '../../store/context';
import classes from './LeftContent.module.scss';
import * as actionTypes from '../../store/actionTypes';
import {
  getFilmByTitleEndpoint,
  getAllFilmsEndpoint,
  insertFilmEndpoint
} from '../../endpoints';
import {
  MDBBtn,
  MDBBtnGroup,
  MDBCol,
  MDBInput,
  MDBRadio
} from 'mdb-react-ui-kit';

const LeftContent = () => {
  const { globalState, actions } = useContext(Context);

  const inputChangedHandler = (event, inputName) => {
    actions({
      type: actionTypes.setFormData,
      payload: { ...globalState.formData, [inputName]: event.target.value }
    });
  };

  return (
    <MDBCol size="md-3" className={classes.LeftContent}>
      <h3>Format: </h3>
      <MDBBtnGroup className={classes.FormatRadioGroup}>
        <MDBRadio
          name="formatGroup"
          id="inlineRadio4"
          value="json"
          label="JSON (default)"
          inline
          onClick={() =>
            actions({ type: actionTypes.setFormat, payload: 'json' })
          }
        />
        <MDBRadio
          name="formatGroup"
          id="inlineRadio5"
          value="xml"
          label="XML"
          inline
          onClick={() =>
            actions({ type: actionTypes.setFormat, payload: 'xml' })
          }
        />
        <MDBRadio
          name="formatGroup"
          id="inlineRadio6"
          value="csv"
          label="csv"
          inline
          onClick={() =>
            actions({ type: actionTypes.setFormat, payload: 'csv' })
          }
        />
      </MDBBtnGroup>

      <MDBBtnGroup className={classes.OperationRadioGroup}>
        <MDBRadio
          name="operationGroup"
          id="inlineRadio1"
          label="Get all films"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: getAllFilmsEndpoint
            })
          }
        />
        <MDBRadio
          name="operationGroup"
          id="inlineRadio2"
          label="Get film by title"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: getFilmByTitleEndpoint
            })
          }
        />
        <MDBRadio
          name="operationGroup"
          id="inlineRadio3"
          label="Add new film"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: insertFilmEndpoint
            })
          }
        />
      </MDBBtnGroup>

      {globalState.endpoint === getFilmByTitleEndpoint ? (
        <MDBInput
          className={classes.Input}
          label="Title"
          id="titleForm"
          type="text"
          onChange={(event) => {
            inputChangedHandler(event, 'filmTitle');
          }}
        />
      ) : null}

      {globalState.endpoint === getAllFilmsEndpoint ||
      globalState.endpoint === getFilmByTitleEndpoint ? (
        <MDBBtn
          onClick={() =>
            actions({ type: actions.setShouldGetFilm, payload: true })
          }
        >
          Get film(s)
        </MDBBtn>
      ) : null}

      {globalState.endpoint === insertFilmEndpoint ? (
        <>
          <h3>Film attributes:</h3>

          <form
            onSubmit={(event) => {
              event.preventDefault();
            }}
          >
            <MDBInput
              className={classes.Input}
              label="Title"
              type="text"
              onChange={(event) => inputChangedHandler(event, 'title')}
            />
            <MDBInput
              className={classes.Input}
              label="Year"
              type="text"
              onChange={(event) => inputChangedHandler(event, 'year')}
            />
            <MDBInput
              className={classes.Input}
              label="Director"
              type="text"
              onChange={(event) => inputChangedHandler(event, 'director')}
            />
            <MDBInput
              className={classes.Input}
              label="Stars"
              type="text"
              onChange={(event) => inputChangedHandler(event, 'stars')}
            />
            <MDBInput
              className={classes.Input}
              label="Review"
              type="text"
              onChange={(event) => inputChangedHandler(event, 'review')}
            />

            <MDBBtn
              onClick={() =>
                actions({ type: actions.setShouldPostFilm, payload: true })
              }
            >
              Create new film
            </MDBBtn>
          </form>
        </>
      ) : null}
    </MDBCol>
  );
};

export default LeftContent;
