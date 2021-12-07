import React, { useEffect, useState } from 'react';
import classes from './Home.module.scss';
import {
  MDBContainer,
  MDBBtn,
  MDBBtnGroup,
  MDBCol,
  MDBInput,
  MDBRadio,
  MDBRow
} from 'mdb-react-ui-kit';
import { getAllFilmsEndpoint, getFilmByTitleEndpoint } from '../../endpoints';
import RightContent from '../../components/RightContent/RightContent';
import MiddleContent from '../../components/MiddleContent/MiddleContent';
import { insertFilmEndpoint } from './../../endpoints';
import createHTTPRequest from './../../utils/createHTTPRequest';

const Home = () => {
  const [endpoint, setEndpoint] = useState('');
  const [films, setFilms] = useState();
  const [formData, setFormData] = useState({});
  const [format, setFormat] = useState('json');
  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);

  useEffect(() => {
    async function postFilm() {
      await createHTTPRequest(insertFilmEndpoint, 'POST', formData);
    }

    if (shouldPostFilm) postFilm();
  }, [shouldPostFilm]);

  useEffect(() => {
    async function getFilms() {
      const url = generateEndpoint();

      switch (format) {
        case 'json':
          setFilms(await getJSONFilms(url));
          break;
        case 'xml':
          setFilms(await getXMLFilms(url));
          break;
        // case 'csv':
        //   films = getCSVFilms(url);
        //   break;
        default:
          setFilms(await getJSONFilms(url));
      }
    }

    if (shouldGetFilm) getFilms();
  }, [shouldGetFilm]);

  const getJSONFilms = async (url) => {
    return await createHTTPRequest(url, 'GET', null);
  };

  // return content from xml request
  const getXMLFilms = async (url) => {
    let response = await fetch(url, {
      method: 'GET'
    });
    response = await response.text();

    const xml = new DOMParser().parseFromString(response, 'application/xml');
    return new XMLSerializer().serializeToString(xml.documentElement);
  };

  const generateEndpoint = () => {
    let localEndpoint = endpoint.concat(`?format=${format}`);

    if (formData.filmTitle)
      localEndpoint = localEndpoint.concat(`&title=${formData.filmTitle}`);

    return localEndpoint;
  };

  const inputChangedHandler = (event, inputName) => {
    setFormData({ ...formData, [inputName]: event.target.value });
  };

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <MDBCol size="md-3" className={classes.LeftContent}>
          <h3>Format: </h3>
          <MDBBtnGroup className={classes.FormatRadioGroup}>
            <MDBRadio
              name="formatGroup"
              id="inlineRadio4"
              value="json"
              label="JSON (default)"
              inline
              onClick={() => setFormat('json')}
            />
            <MDBRadio
              name="formatGroup"
              id="inlineRadio5"
              value="xml"
              label="XML"
              inline
              onClick={() => setFormat('xml')}
            />
            <MDBRadio
              name="formatGroup"
              id="inlineRadio6"
              value="csv"
              label="csv"
              inline
              onClick={() => setFormat('csv')}
            />
          </MDBBtnGroup>

          <MDBBtnGroup className={classes.OperationRadioGroup}>
            <MDBRadio
              name="operationGroup"
              id="inlineRadio1"
              label="Get all films"
              inline
              onClick={() => setEndpoint(getAllFilmsEndpoint)}
            />
            <MDBRadio
              name="operationGroup"
              id="inlineRadio2"
              label="Get film by title"
              inline
              onClick={() => setEndpoint(getFilmByTitleEndpoint)}
            />
            <MDBRadio
              name="operationGroup"
              id="inlineRadio3"
              label="Add new film"
              inline
              onClick={() => setEndpoint(insertFilmEndpoint)}
            />
          </MDBBtnGroup>

          {endpoint === getFilmByTitleEndpoint ? (
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

          {endpoint === getAllFilmsEndpoint ||
          endpoint === getFilmByTitleEndpoint ? (
            <MDBBtn onClick={() => setShouldGetFilm(true)}>Get film(s)</MDBBtn>
          ) : null}

          {endpoint === insertFilmEndpoint ? (
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

        <div className={classes.ContentWrapper}>
          <MiddleContent films={films} />
          <RightContent films={films} />
        </div>
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
