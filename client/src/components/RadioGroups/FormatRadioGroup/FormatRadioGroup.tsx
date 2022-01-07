import { MDBBtnGroup } from 'mdb-react-ui-kit';
import React from 'react';
import Radio from '../../Radio/Radio';
import classes from './FormatRadioGroup.module.scss';

interface IProps {
  onClick: () => void;
}

const FormatRadioGroup: React.FC<IProps> = ({ onClick }) => {
  return (
    <MDBBtnGroup className={classes.FormatRadioGroup}>
      <Radio defaultChecked label="JSON" id={'json'} name="formatGroup" onClick={onClick} />
      <Radio label="XML" id={'xml'} name="formatGroup" onClick={onClick} />
      <Radio label="Text" id={'csv'} name="formatGroup" onClick={onClick} />
    </MDBBtnGroup>
  );
};

export default FormatRadioGroup;
