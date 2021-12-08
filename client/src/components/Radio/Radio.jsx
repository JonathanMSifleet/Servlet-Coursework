import { MDBRadio } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Radio.module.scss';

const Radio = ({ label, name }) => {
  return (
    <div className={classes.Wrapper}>
      <MDBRadio
        className={classes.Radio}
        inline
        label={label}
        name={name}
        value={label}
      />
    </div>
  );
};

export default Radio;
