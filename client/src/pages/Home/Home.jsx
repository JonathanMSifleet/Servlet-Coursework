import React, { useEffect, useState, useContext } from 'react';
import Context from '../../store/context';
import classes from './Home.module.scss';
import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import RightContent from '../../components/RightContent/RightContent';
import MiddleContent from '../../components/MiddleContent/MiddleContent';
import { insertFilmEndpoint } from './../../endpoints';
import createHTTPRequest from './../../utils/createHTTPRequest';
import LeftContent from './../../components/LeftContent/LeftContent';

const Home = () => {
  const [films, setFilms] = useState();

  const { globalState } = useContext(Context);

  useEffect(() => {
    async function postFilm() {
      await createHTTPRequest(insertFilmEndpoint, 'POST', globalState.formData);
    }

    if (globalState.shouldPostFilm) postFilm();
  }, [globalState.shouldPostFilm]);

  useEffect(() => {
    async function getFilms() {
      const url = generateEndpoint();

      switch (globalState.format) {
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

    if (globalState.shouldGetFilm) getFilms();
  }, [globalState.shouldGetFilm]);

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
    let localEndpoint = globalState.endpoint.concat(
      `?format=${globalState.format}`
    );

    if (globalState.formData.filmTitle)
      localEndpoint = localEndpoint.concat(
        `&title=${globalState.formData.filmTitle}`
      );

    return localEndpoint;
  };

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <LeftContent />

        <div className={classes.ContentWrapper}>
          <MiddleContent films={films} />
          <RightContent films={films} />
        </div>
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
