import { MDBInput } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Input.module.scss';

const Input = ({ className, label, onChange, placeholder }) => {
  return (
    <MDBInput
      className={`${classes.Input} ${className}`}
      label={label}
      onChange={onChange}
      placeholder={placeholder}
      type="text"
    />
  );
};

export default Input;