import React, { useContext, useState, useEffect } from 'react';
import Context from '../../../store/context';
import classes from './LeftContent.module.scss';
import * as actionTypes from '../../../store/actionTypes';
import createHTTPRequest from '../../../utils/createHTTPRequest';
import * as endpoints from '../../../endpoints';
import Radio from './../../../components/Radio/Radio';
import { MDBBtn, MDBCol, MDBInput, MDBRadio } from 'mdb-react-ui-kit';

const LeftContent = () => {
  const { globalState, actions } = useContext(Context);

  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);
  const [format, setFormat] = useState('json');

  const inputChangedHandler = (event, inputName) => {
    actions({
      type: actionTypes.setFormData,
      payload: { ...globalState.formData, [inputName]: event.target.value }
    });
  };

  useEffect(() => {
    async function postFilm() {
      await createHTTPRequest(
        endpoints.insertFilmEndpoint,
        'POST',
        globalState.formData
      );
    }

    if (shouldPostFilm) postFilm();
  }, [shouldPostFilm]);

  useEffect(() => {
    async function getFilms() {
      const url = generateEndpoint();

      switch (format) {
        case 'json':
          actions({
            type: actionTypes.setFilms,
            payload: await createHTTPRequest(url, 'GET')
          });
          break;
        case 'xml':
          actions({
            type: actionTypes.setFilms,
            payload: await getXMLFilms(url)
          });
          break;
        // case 'csv':
        //   films = getCSVFilms(url);
        //   break;
        default:
          actions({
            type: actionTypes.setFilms,
            payload: await createHTTPRequest(url, 'GET')
          });
      }
    }

    if (shouldGetFilm) getFilms();
  }, [shouldGetFilm]);

  const getXMLFilms = async (url) => {
    let response = await fetch(url, {
      method: 'GET'
    });
    response = await response.text();

    const xml = new DOMParser().parseFromString(response, 'application/xml'); // document empty
    return new XMLSerializer().serializeToString(xml.documentElement);
  };

  const generateEndpoint = () => {
    let localEndpoint = globalState.endpoint.concat(`?format=${format}`);

    if (globalState.formData.filmTitle)
      localEndpoint = localEndpoint.concat(
        `&title=${globalState.formData.filmTitle}`
      );

    return localEndpoint;
  };

  return (
    <MDBCol size="md-3" className={classes.LeftContent}>
      <h3>Format: </h3>
      <div className={classes.FormatRadioGroup}>
        <Radio label="JSON" />
        {/* <Radio label="JSON" onChange={setFormat('json')} /> */}

        {/* <MDBRadio
          className={classes.Radio}
          name="formatGroup"
          id="inlineRadio4"
          value="json"
          label="JSON"
          inline
          onClick={() => setFormat('json')}
        /> */}
        <MDBRadio
          className={classes.Radio}
          name="formatGroup"
          id="inlineRadio5"
          value="xml"
          label="XML"
          inline
          onClick={() => setFormat('xml')}
        />
        <MDBRadio
          className={classes.Radio}
          name="formatGroup"
          id="inlineRadio6"
          value="csv"
          label="csv"
          inline
          onClick={() => setFormat('csv')}
        />
      </div>

      <div className={classes.OperationRadioGroup}>
        <MDBRadio
          className={classes.Radio}
          name="operationGroup"
          id="inlineRadio1"
          label="Get all films"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.getAllFilmsEndpoint
            })
          }
        />
        <MDBRadio
          className={classes.Radio}
          name="operationGroup"
          id="inlineRadio2"
          label="Get film by title"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.getFilmByTitleEndpoint
            })
          }
        />
        <MDBRadio
          className={`${classes.Radio} ${classes.EndRadio}`}
          name="operationGroup"
          id="inlineRadio3"
          label="Add new film"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.insertFilmEndpoint
            })
          }
        />
        <MDBRadio
          className={classes.Radio}
          name="operationGroup"
          id="inlineRadio3"
          label="Update film"
          inline
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.updateFilmEndpoint
            })
          }
        />
      </div>

      {globalState.endpoint === endpoints.getFilmByTitleEndpoint ? (
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

      {globalState.endpoint === endpoints.getAllFilmsEndpoint ||
      globalState.endpoint === endpoints.getFilmByTitleEndpoint ? (
        <MDBBtn onClick={() => setShouldGetFilm(true)}>Get film(s)</MDBBtn>
      ) : null}

      {globalState.endpoint === endpoints.insertFilmEndpoint ? (
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

            <MDBBtn onClick={() => setShouldPostFilm(true)}>
              Create new film
            </MDBBtn>
          </form>
        </>
      ) : null}
    </MDBCol>
  );
};

export default LeftContent;
