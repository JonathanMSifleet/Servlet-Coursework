import { MDBCol } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './RightContent.module.scss';

const RightContent = ({ films }) => {
  return (
    <MDBCol className={classes.RightContent}>
      <h1 className={classes.Header}>Raw</h1>
      <pre id="json">{JSON.stringify(films, null, 4)} </pre>
    </MDBCol>
  );
};

export default RightContent;
