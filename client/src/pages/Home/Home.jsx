import React, { useContext } from 'react';
import Context from '../../store/context';
import classes from './Home.module.scss';
import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import RightContent from './RightContent/RightContent';
import MiddleContent from './MiddleContent/MiddleContent';
import LeftContent from './LeftContent/LeftContent';

const Home = () => {
  const { globalState } = useContext(Context);

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <LeftContent />

        <div className={classes.ContentWrapper}>
          <MiddleContent films={globalState.films} />
          <RightContent films={globalState.films} />
        </div>
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
