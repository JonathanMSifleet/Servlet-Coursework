import { MDBRadio } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Radio.module.scss';

const Radio = ({ onClick, label }) => {
  return (
    <div className={classes.Wrapper}>
      <MDBRadio
        className={classes.Radio}
        value={label}
        label={label}
        inline
        // onClick={onClick}
      />
    </div>
  );
};

export default Radio;
