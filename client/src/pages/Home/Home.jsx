import React from 'react';
import ControlPanel from './Content/ControlPanel';
import classes from './Home.module.scss';
import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';

const Home = () => {
  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <ControlPanel />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
