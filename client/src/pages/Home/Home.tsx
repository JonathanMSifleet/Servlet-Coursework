import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import React from 'react';
import classes from './Home.module.scss';
import SidePanel from './SidePanel/SidePanel';

const Home: React.FC = () => {
  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <SidePanel />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
