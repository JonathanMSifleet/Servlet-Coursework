import React from 'react';
import classes from './MiddleContent.module.scss';
import { MDBCol, MDBCard, MDBCardBody, MDBCardTitle } from 'mdb-react-ui-kit';

const MiddleContent = ({ films, format }) => {
  const handleFormat = () => {
    let preparedFilms;

    switch (format) {
      case 'json':
        preparedFilms = films;
        break;
      case 'xml':
        preparedFilms = handleXML(films);
        break;
      case 'csv':
        break;
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
    console.log(
      '🚀 ~ file: MiddleContent.jsx ~ line 29 ~ handleXML ~ deserialisedFilms',
      deserialisedFilms
    );

    console.log(convertXMLtoJSON(deserialisedFilms));

    // to do: xml to json
  };

  const convertXMLtoJSON = (xml) => {
    const json = {};
    let children = xml.children;
    let childrenLength = children.length;

    for (let i = 0; i < childrenLength; i++) {
      let child = children[i];
      let childName = child.nodeName;
      let childValue = child.textContent;

      if (child.children.length > 0) {
        childValue = convertXMLtoJSON(child);
      }

      json[childName] = childValue;
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
