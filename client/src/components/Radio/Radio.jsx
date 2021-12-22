import React from 'react';
import classes from './Radio.module.scss';
import { MDBRadio } from 'mdb-react-ui-kit';

const Radio = ({ className, defaultChecked, label, name, onClick }) => {
  return (
    <div className={`${className} ${classes.Wrapper}`}>
      <MDBRadio
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
