import { MDBCol } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './RightContent.module.scss';

const RightContent = ({ films }) => {
  const handleFormat = () => {
    switch (typeof films) {
      case 'object':
        return JSON.stringify(films, null, 4);
      case 'string':
        return films;
      // case 'csv':
      //   break;
      default:
        return null;
    }
  };

  return (
    <MDBCol className={classes.RightContent}>
      <h1 className={classes.Header}>Raw</h1>
      <pre className={classes.Output}>
        {() => {
          const output = handleFormat();
          output ? output : null;
        }}
      </pre>
    </MDBCol>
  );
};

export default RightContent;
