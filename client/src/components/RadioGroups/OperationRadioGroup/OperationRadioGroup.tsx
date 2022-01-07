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
        id="getAllFilms"
        label="Get all films"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.TopOperationRadio}
        id="getFilmByTitle"
        label="Get film by title"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.TopOperationRadio}
        id="insertFilm"
        label="Add new film"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.BottomOperationRadio}
        id="getFilmByID"
        label="Update film"
        name="operationGroup"
        onClick={onClick}
      />
      <Radio
        className={classes.BottomOperationRadio}
        id="deleteFilm"
        label="Delete film"
        name="operationGroup"
        onClick={onClick}
      />
    </MDBBtnGroup>
  );
};

export default OperationRadioGroup;
