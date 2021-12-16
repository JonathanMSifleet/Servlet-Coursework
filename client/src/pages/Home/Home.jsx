import React from 'react';
import Content from './Content/Content';
import classes from './Home.module.scss';
import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';

const Home = () => {
  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <Content />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
