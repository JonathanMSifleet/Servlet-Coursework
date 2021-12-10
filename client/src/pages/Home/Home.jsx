import React, { useContext } from 'react';
import Context from '../../store/context';
import classes from './Home.module.scss';
import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import RightContent from './RightContent/RightContent';
import LeftContent from './LeftContent/LeftContent';

const Home = () => {
  const { globalState } = useContext(Context);

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <LeftContent />
        <RightContent films={globalState.films} />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
