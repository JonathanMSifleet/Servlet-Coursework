import { MDBContainer, MDBRow } from 'mdb-react-ui-kit';
import { useState } from 'react';
import IFilm from '../../interfaces/IFilm';
import classes from './Home.module.scss';
import Output from './Output/Output';
import SidePanel from './SidePanel/SidePanel';

const Home: React.FC = () => {
  const [films, setFilms] = useState(null as unknown as IFilm[] | string | null);
  const [format, setFormat] = useState('json');
  const [formatChanged, setFormatChanged] = useState(false);
  const [selectedFilmID, setSelectedFilmID] = useState(null as number | null);

  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
        <SidePanel
          format={format}
          selectedFilmID={selectedFilmID!}
          setFilms={(films: IFilm[] | string | null): void => setFilms(films)}
          setFormat={(format: string): void => setFormat(format)}
          setFormatChanged={(formatChanged: boolean): void => setFormatChanged(formatChanged)}
          setSelectedFilmID={(selectedFilmID: number | null): void =>
            setSelectedFilmID(selectedFilmID)
          }
        />
        <Output
          films={films as IFilm[] | string | null}
          format={format}
          formatChanged={formatChanged}
          setSelectedFilmID={(selectedFilmID: number | null): void =>
            setSelectedFilmID(selectedFilmID)
          }
        />
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
