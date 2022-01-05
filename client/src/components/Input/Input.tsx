import { MDBInput } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Input.module.scss';

interface IProps {
  className?: string;
  label: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  value?: string;
}

const Input: React.FC<IProps> = ({ className, label, onChange, placeholder, value }) => {
  return (
    <MDBInput
      className={`${classes.Input} ${className}`}
      label={label}
      onChange={onChange}
      placeholder={placeholder}
      type="text"
      value={value}
    />
  );
};

export default Input;
