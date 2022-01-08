import { MDBCol, MDBTable, MDBTableBody, MDBTableHead } from 'mdb-react-ui-kit';
import IFilm from '../../../interfaces/IFilm';
import csvToJSON from '../../../utils/csvToJSON';
import classes from './Output.module.scss';

interface IProps {
  films: string | IFilm[] | null;
  format: string;
  formatChanged: boolean;
  setSelectedFilmID: (id: number) => void;
}

const Output: React.FC<IProps> = ({ films, format, formatChanged, setSelectedFilmID }) => {
  const handleFormat = (): JSX.Element | null => {
    if (formatChanged) return null;

    switch (format) {
      case 'xml':
        return printFilms(xmlToJSON(films as string));
      case 'csv':
        return printFilms(convertCSVToJSON(films as string));
      default:
        return printFilms(films as IFilm[]);
    }
  };

  const xmlToJSON = (xmlToParse: string): IFilm[] => {
    const parsedXML = new DOMParser().parseFromString(xmlToParse, 'application/xml');
    const xmlDocument = parsedXML.getElementsByTagName('root')[0].children;

    const json: IFilm[] = [];

    for (let i = 0; i < xmlDocument.length; i++) {
      const child = xmlDocument[i];

      const id = parseInt(child.getElementsByTagName('id')[0].textContent!);
      const title = child.getElementsByTagName('title')[0].textContent!;
      const year = parseInt(child.getElementsByTagName('year')[0].textContent!);
      const director = child.getElementsByTagName('director')[0].textContent!;
      const stars = child.getElementsByTagName('stars')[0].textContent!;
      const review = child.getElementsByTagName('review')[0].textContent!;

      json.push({ id, title, year, director, stars, review });
    }
    return json;
  };

  const convertCSVToJSON = (csv: string): IFilm[] => {
    const lines = csv.split('\n');
    const json: IFilm[] = [];

    lines.forEach((csvFilm) => {
      json.push(csvToJSON(csvFilm));
    });

    return json;
  };

  const printFilms = (preparedFilms: IFilm[]): JSX.Element => {
    return (
      <MDBTable className={classes.FilmTable} striped>
        <MDBTableHead>
          <tr>
            <th className={classes.IDCell} scope="col">
              ID
            </th>
            <th className={classes.TitleCell} scope="col">
              Title
            </th>
            <th className={classes.YearCell} scope="col">
              Year
            </th>
            <th className={classes.DirectorCell} scope="col">
              Director
            </th>
            <th className={classes.StarsCell} scope="col">
              Stars
            </th>
            <th className={classes.ReviewTextCell} scope="col">
              Review
            </th>
          </tr>
        </MDBTableHead>
        <MDBTableBody>
          {preparedFilms
            ? preparedFilms.map((film: IFilm) => {
                return (
                  <tr key={film.id} onClick={(): void => setSelectedFilmID(film.id!)}>
                    <td className={classes.IDCell}>{film.id}</td>
                    <td className={classes.TitleCell}>{film.title}</td>
                    <td className={classes.YearCell}>{film.year}</td>
                    <td className={classes.DirectorCell}>{film.director}</td>
                    <td className={classes.StarsCell}>{film.stars}</td>
                    <td className={classes.ReviewTextCell}>
                      <div className={classes.ReviewText}>{film.review}</div>
                    </td>
                  </tr>
                );
              })
            : null}
        </MDBTableBody>
      </MDBTable>
    );
  };

  return (
    <MDBCol className={classes.RightContent}>
      <h1 className={classes.Header}>Films:</h1>
      <ul className={classes.List}>{films ? handleFormat() : null}</ul>
    </MDBCol>
  );
};

export default Output;
