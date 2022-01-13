import { MDBBtn } from 'mdb-react-ui-kit';
import classes from './Button.module.scss';

interface IProps {
  onClick(): void;
  text: string;
}

const Button: React.FC<IProps> = ({ onClick, text }) => {
  return (
    <div className={classes.ButtonWrapper}>
      <MDBBtn className={classes.Button} onClick={onClick}>
        {text}
      </MDBBtn>
    </div>
  );
};

export default Button;
