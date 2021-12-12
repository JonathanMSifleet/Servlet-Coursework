import { MDBRadio } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Radio.module.scss';

const Radio = ({ defaultChecked, label, name, onClick }) => {
  return (
    <div className={classes.Wrapper}>
      <MDBRadio
        className={classes.Radio}
        defaultChecked={defaultChecked}
        inline
        label={label}
        name={name}
        onClick={onClick}
        value={label}
      />
    </div>
  );
};

export default Radio;
