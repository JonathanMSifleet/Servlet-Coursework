import React from 'react';
import classes from './MiddleContent.module.scss';
import { MDBCol, MDBCard, MDBCardBody, MDBCardTitle } from 'mdb-react-ui-kit';

const MiddleContent = ({ films }) => {
  const handleFormat = () => {
    let preparedFilms;

    switch (typeof films) {
      case 'object':
        preparedFilms = films;
        break;
      case 'string':
        preparedFilms = handleXML(films);
        break;
      // case 'csv':
      //   break;
      default:
        return null;
    }

    return printFilms(preparedFilms);
  };

  const handleXML = (films) => {
    const deserialisedFilms = new DOMParser().parseFromString(
      films,
      'application/xml'
    );

    try {
      return convertXMLtoJSON(deserialisedFilms);
      // eslint-disable-next-line no-empty
    } catch (e) {
      console.error(e);
    }
  };

  const convertXMLtoJSON = (xml) => {
    const json = [];

    const localXML = xml.getElementsByTagName('root')[0].children;

    for (let i = 0; i < localXML.length; i++) {
      const child = localXML[i];

      const id = child.getElementsByTagName('id')[0].textContent;
      const title = child.getElementsByTagName('title')[0].textContent;
      const year = child.getElementsByTagName('year')[0].textContent;
      const director = child.getElementsByTagName('director')[0].textContent;
      const stars = child.getElementsByTagName('stars')[0].textContent;
      const review = child.getElementsByTagName('review')[0].textContent;

      json.push({ id, title, year, director, stars, review });
    }
    return json;
  };

  const printFilms = (preparedFilms) => {
    return (
      <>
        {preparedFilms
          ? preparedFilms.map((film) => {
              return (
                <li key={film.id}>
                  <MDBCard className={classes.Card}>
                    <MDBCardBody className={classes.CardBody}>
                      <MDBCardTitle>
                        {film.title} ({film.year})
                      </MDBCardTitle>
                      <p>
                        <b>Director:</b> {film.director}
                      </p>
                      <p>
                        <b>Starring:</b> {film.stars}
                      </p>
                      <p className={classes.ReviewText}>
                        <b>Review:</b> {film.review}
                      </p>
                    </MDBCardBody>
                  </MDBCard>
                </li>
              );
            })
          : null}
      </>
    );
  };

  return (
    <MDBCol className={classes.MiddleContent}>
      <h1 className={classes.Header}>Formatted</h1>
      <ul className={classes.List}>{handleFormat()}</ul>
    </MDBCol>
  );
};

export default MiddleContent;
