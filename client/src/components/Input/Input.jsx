import { MDBInput } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Input.module.scss';

const Input = ({ label, onChange }) => {
  return (
    <MDBInput
      className={classes.Input}
      label={label}
      type="text"
      onChange={onChange}
    />
  );
};

export default Input;
