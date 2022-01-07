import { MDBBtn } from 'mdb-react-ui-kit';
import classes from './Button.module.scss';

interface IProps {
  className?: string;
  onClick(): void;
  text: string;
}

const Button: React.FC<IProps> = ({ className, onClick, text }) => {
  return (
    <div className={classes.ButtonWrapper}>
      <MDBBtn className={`${classes.Button} ${className}`} onClick={onClick}>
        {text}
      </MDBBtn>
    </div>
  );
};

export default Button;
