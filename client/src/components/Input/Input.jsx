import { MDBInput } from 'mdb-react-ui-kit';
import React, { useContext } from 'react';
import classes from './Input.module.scss';
import Context from '../../store/context';
import * as actionTypes from '../../store/actionTypes';

const Input = ({ label }) => {
  const inputChangedHandler = (event, inputName) => {
    const { globalState, actions } = useContext(Context);

    actions({
      type: actionTypes.setFormData,
      payload: { ...globalState.formData, [inputName]: event.target.value }
    });
  };

  return (
    <MDBInput
      className={classes.Input}
      label={label}
      type="text"
      onChange={(event) => {
        inputChangedHandler(event, label.toLowerCase());
      }}
    />
  );
};

export default Input;
