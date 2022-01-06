import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import React from 'react';
import ControlPanel from './ControlPanel/ControlPanel';
import classes from './Home.module.scss';

const Home: React.FC = () => {
  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <ControlPanel />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
