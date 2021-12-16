import React from 'react';
import classes from './Output.module.scss';
import { MDBCol, MDBTable, MDBTableBody, MDBTableHead } from 'mdb-react-ui-kit';

const Output = ({ films, format, formatChanged, sharedSetSelectedFilmID }) => {
  const handleFormat = () => {
    if (formatChanged) return null;

    let preparedFilms;

    switch (format) {
      case 'xml':
        preparedFilms = xmlToJSON(films);
        break;
      case 'csv':
        preparedFilms = convertCSVToJSON(films);
        break;
      default:
        preparedFilms = films;
    }

    return printFilms(preparedFilms);
  };

  const xmlToJSON = (xml) => {
    xml = new DOMParser().parseFromString(xml, 'application/xml');
    xml = xml.getElementsByTagName('root')[0].children;

    const json = [];

    for (let i = 0; i < xml.length; i++) {
      const child = xml[i];

      const id = child.getElementsByTagName('id')[0].textContent;
      const title = child.getElementsByTagName('title')[0].textContent;
      const year = child.getElementsByTagName('year')[0].textContent;
      const director = child.getElementsByTagName('director')[0].textContent;
      const stars = child.getElementsByTagName('stars')[0].textContent;
      const review = child.getElementsByTagName('review')[0].textContent;

      json.push({ id, title, year, director, stars, review });
    }
    return json;
  };

  const convertCSVToJSON = (csv) => {
    const lines = csv.split('\n');
    const json = [];

    lines.forEach((csvFilm) => {
      let film = csvFilm.split(',,');
      json.push({ id: film[0], title: film[1], year: film[2], director: film[3], stars: film[4], review: film[5] });
    });

    return json;
  };

  const printFilms = (preparedFilms) => {
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
            <th className={classes.Review} scope="col">
              Review
            </th>
          </tr>
        </MDBTableHead>
        <MDBTableBody>
          {preparedFilms
            ? preparedFilms.map((film) => {
                return (
                  <tr key={film.id} onClick={() => sharedSetSelectedFilmID(film.id)}>
                    <td className={classes.IDCell}>{film.id}</td>
                    <td className={classes.TitleCell}>{film.title}</td>
                    <td className={classes.YearCell}>{film.year}</td>
                    <td className={classes.DirectorCell}>{film.director}</td>
                    <td className={classes.StarsCell}>{film.stars}</td>
                    <td>
                      <div className={classes.ReviewText}>{film.review}...</div>
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
