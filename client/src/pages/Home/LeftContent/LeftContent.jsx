import React, { useContext, useState, useEffect } from 'react';
import Context from '../../../store/context';
import classes from './LeftContent.module.scss';
import * as actionTypes from '../../../store/actionTypes';
import createHTTPRequest from '../../../utils/createHTTPRequest';
import * as endpoints from '../../../endpoints';
import Radio from './../../../components/Radio/Radio';
import { MDBBtn, MDBBtnGroup, MDBCol } from 'mdb-react-ui-kit';
import Input from './../../../components/Input/Input';

const LeftContent = ({ endpoint, formData, format }) => {
  const { actions } = useContext(Context);

  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);

  useEffect(() => {
    async function postFilm() {
      await createHTTPRequest(endpoints.insertFilmEndpoint, 'POST', formData);
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
    let localEndpoint = endpoint.concat(`?format=${format}`);

    if (formData.title)
      localEndpoint = localEndpoint.concat(`&title=${formData.title}`);

    return localEndpoint;
  };

  return (
    <MDBCol size="md-3" className={classes.LeftContent}>
      <h3>Format: </h3>
      <MDBBtnGroup className={classes.FormatRadioGroup}>
        <Radio
          label="JSON"
          name="formatGroup"
          // onClick={}
        />
        <Radio
          label="XML"
          name="formatGroup"
          // onClick={}
        />
        <Radio
          label="Text"
          name="formatGroup"
          // onClick={}
        />
      </MDBBtnGroup>

      <MDBBtnGroup className={classes.OperationRadioGroup}>
        <Radio
          name="operationGroup"
          label="Get all films"
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.getAllFilmsEndpoint
            })
          }
        />
        <Radio
          name="operationGroup"
          label="Get film by title"
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.getFilmByTitleEndpoint
            })
          }
        />
        <Radio
          name="operationGroup"
          label="Add new film"
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.insertFilmEndpoint
            })
          }
        />
        <Radio
          name="operationGroup"
          label="Update film"
          onClick={() =>
            actions({
              type: actionTypes.setEndpoint,
              payload: endpoints.updateFilmEndpoint
            })
          }
        />
      </MDBBtnGroup>

      {endpoint === endpoints.getFilmByTitleEndpoint ? (
        <Input label="title" />
      ) : null}

      {endpoint === endpoints.getAllFilmsEndpoint ||
      endpoint === endpoints.getFilmByTitleEndpoint ? (
        <MDBBtn onClick={() => setShouldGetFilm(true)}>Get film(s)</MDBBtn>
      ) : null}

      {endpoint === endpoints.insertFilmEndpoint ? (
        <>
          <h3>Film attributes:</h3>

          <form
            onSubmit={(event) => {
              event.preventDefault();
            }}
          >
            <Input label="Title" />
            <Input label="Year" />
            <Input label="Director" />
            <Input label="Stars" />
            <Input label="Title" />
            <Input label="Review" />

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
