import React from 'react';
import classes from './MiddleContent.module.scss';
import { MDBCol, MDBCard, MDBCardBody, MDBCardTitle } from 'mdb-react-ui-kit';

const MiddleContent = ({ films }) => {
  return (
    <MDBCol className={classes.MiddleContent}>
      <h1 className={classes.Header}>Formatted</h1>
      <ul className={classes.List}>
        {films
          ? films.map((film) => {
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
      </ul>
    </MDBCol>
  );
};

export default MiddleContent;
