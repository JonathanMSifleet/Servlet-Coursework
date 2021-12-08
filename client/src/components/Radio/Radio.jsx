import { MDBRadio } from 'mdb-react-ui-kit';
import React, { useContext } from 'react';
import classes from './Radio.module.scss';
import Context from '../../store/context';
import * as actionTypes from '../../store/actionTypes';

const Radio = ({ label, name }) => {
  const { actions } = useContext(Context);

  const handleOnClick = () => {
    switch (name) {
      case 'formatGroup':
        actions({
          type: actionTypes.setFormat,
          payload: label.toLowerCase()
        });
        break;
    }
  };

  return (
    <div className={classes.Wrapper}>
      {console.log('label', label)}
      <MDBRadio
        defaultChecked={label === 'json' ? true : false}
        inline
        label={label}
        onClick={handleOnClick()}
        name={name}
        value={label}
      />
    </div>
  );
};

export default Radio;
