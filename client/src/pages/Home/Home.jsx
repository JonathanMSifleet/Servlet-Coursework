import React, { useEffect, useState } from 'react';
import classes from './Home.module.scss';
import {
  MDBContainer,
  MDBBtn,
  MDBBtnGroup,
  MDBCol,
  MDBRadio,
  MDBRow
} from 'mdb-react-ui-kit';
import { getAllFilmsEndpoint, getFilmByTitleEndpoint } from '../../endpoints';
import RightContent from 'src/components/RightContent/RightContent';
import MiddleContent from 'src/components/MiddleContent/MiddleContent';

const Home = () => {
  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [films, setFilms] = useState();

  const [endpoint, setEndpoint] = useState('');
  const [format, setFormat] = useState('json');

  const [formData, setFormData] = useState({});

  useEffect(() => {
    console.log(format);
  }, [format]);

  useEffect(() => {
    async function getFilms() {
      let response = await fetch(generateEndpoint(), {
        method: 'GET'
      });
      response = await response.json();
      setFilms(response);
    }
    getFilms();
  }, [shouldGetFilm]);

  const generateEndpoint = () => {
    let localEndpoint = endpoint;

    if (formData.filmTitle) {
      localEndpoint = localEndpoint.concat(`title=${formData.filmTitle}`);
    }

    return localEndpoint.concat(`?format=${format}`);
  };

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <MDBCol size="md-3" className={classes.LeftContent}>
          <MDBBtn onClick={() => setShouldGetFilm(true)}>Get</MDBBtn>
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
            />
          </MDBBtnGroup>
          <p> Format: </p>
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
        </MDBCol>
        <MiddleContent films={films ? films : null} />
        <RightContent />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
