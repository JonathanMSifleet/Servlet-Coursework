import { MDBInput } from 'mdb-react-ui-kit';
import classes from './Input.module.scss';

interface IProps {
  className?: string;
  label: string;
  name?: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  value?: string;
}

const Input: React.FC<IProps> = ({ className, label, name, onChange, placeholder, value }) => {
  return (
    <MDBInput
      className={`${classes.Input} ${className}`}
      label={label}
      name={name}
      onChange={onChange}
      placeholder={placeholder}
      type="text"
      value={value}
    />
  );
};

export default Input;
