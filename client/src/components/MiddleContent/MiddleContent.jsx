import React from 'react';
import classes from './MiddleContent.module.scss';
import {
  MDBCol,
  MDBCard,
  MDBCardBody,
  MDBCardText,
  MDBCardTitle
} from 'mdb-react-ui-kit';

const MiddleContent = ({ films }) => {
  return (
    <MDBCol className={classes.RightContent}>
      <ul>
        {films
          ? films.map((film) => {
              return (
                <li key={film.id}>
                  <MDBCard>
                    <MDBCardBody>
                      <MDBCardTitle>
                        {film.title} ({film.year})
                      </MDBCardTitle>
                      <MDBCardText>
                        {film.director} {' \n '}
                        {film.stars} {' \n '}
                        {film.review}
                      </MDBCardText>
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
