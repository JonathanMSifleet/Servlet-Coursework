import { MDBBtnGroup } from 'mdb-react-ui-kit';
import Radio from '../../Radio/Radio';
import classes from './OperationRadioGroup.module.scss';

interface IProps {
  onClick: () => void;
}

const OperationRadioGroup: React.FC<IProps> = ({ onClick }) => {
  return (
    <MDBBtnGroup className={classes.OperationRadioGroup}>
      <Radio
        className={classes.TopOperationRadio}
        id="GET_ALL_FILMS"
        label="Get all films"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.TopOperationRadio}
        id="GET_FILM_BY_TITLE"
        label="Get film by title"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.TopOperationRadio}
        id="CREATE_FILM"
        label="Add new film"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.BottomOperationRadio}
        id="UPDATE_FILM"
        label="Update film"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.BottomOperationRadio}
        id="DELETE_FILM"
        label="Delete film"
        name="operationGroup"
        onClick={onClick}
      />
    </MDBBtnGroup>
  );
};

export default OperationRadioGroup;
