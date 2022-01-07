import { MDBRadio } from 'mdb-react-ui-kit';
import classes from './Radio.module.scss';

interface IProps {
  children?: React.ReactNode;
  className?: string;
  defaultChecked?: boolean;
  id: string;
  label: string;
  name: string;
  onClick: () => void;
}

const Radio: React.FC<IProps> = ({ className, defaultChecked, id, label, name, onClick }) => {
  return (
    <div className={`${className} ${classes.Wrapper}`}>
      <MDBRadio
        defaultChecked={defaultChecked}
        id={id}
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
