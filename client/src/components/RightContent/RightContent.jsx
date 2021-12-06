import { MDBCol } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './RightContent.module.scss';

const RightContent = ({ films, format }) => {
  const handleFormat = () => {
    switch (format) {
      case 'json':
        return JSON.stringify(films, null, 4);
      case 'xml':
        return films;
      case 'csv':
        break;
      default:
        return null;
    }
  };

  return (
    <MDBCol className={classes.RightContent}>
      <h1 className={classes.Header}>Raw</h1>
      <pre className={classes.Output}>{handleFormat()}</pre>
    </MDBCol>
  );
};

export default RightContent;
