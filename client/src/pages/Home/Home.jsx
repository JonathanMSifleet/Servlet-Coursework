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

const Home = () => {
  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);

  const [endpoint, setEndpoint] = useState('');
  const [films, setFilms] = useState();
  const [format, setFormat] = useState('json');

  const [formData, setFormData] = useState({});

  useEffect(() => {}, []);

  useEffect(() => {
    async function postFilm() {
      await fetch(insertFilmEndpoint, {
        method: 'POST',
        body: JSON.stringify(formData)
      });
    }

    if (shouldPostFilm) {
      postFilm();
      cleanup();
    }
  }, [shouldPostFilm]);

  useEffect(() => {
    async function getFilms() {
      const url = generateEndpoint();
      console.log('🚀 ~ file: Home.jsx ~ line 27 ~ getFilms ~ url', url);

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

    if (shouldGetFilm) {
      getFilms();
      cleanup();
    }
  }, [shouldGetFilm]);

  const cleanup = () => {
    setShouldGetFilm(false);
    setShouldPostFilm(false);

    setEndpoint(null);
    setFormat('json');

    setFormData({});
  };

  const getJSONFilms = async (url) => {
    const response = await fetch(url, {
      method: 'GET'
    });

    return await response.json();
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
                  label="Title"
                  id="form1"
                  type="text"
                  onChange={(event) => inputChangedHandler(event, 'title')}
                />
                <MDBInput
                  label="Year"
                  id="form1"
                  type="text"
                  onChange={(event) => inputChangedHandler(event, 'year')}
                />
                <MDBInput
                  label="Director"
                  id="form1"
                  type="text"
                  onChange={(event) => inputChangedHandler(event, 'director')}
                />
                <MDBInput
                  label="Stars"
                  id="form1"
                  type="text"
                  onChange={(event) => inputChangedHandler(event, 'stars')}
                />
                <MDBInput
                  label="Review"
                  id="form1"
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
