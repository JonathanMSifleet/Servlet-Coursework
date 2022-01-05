import { MDBRadio } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Radio.module.scss';

interface IProps {
  children?: React.ReactNode;
  className?: string;
  defaultChecked?: boolean;
  label: string;
  name: string;
  onClick: () => void;
}

const Radio: React.FC<IProps> = ({ className, defaultChecked, label, name, onClick }) => {
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
