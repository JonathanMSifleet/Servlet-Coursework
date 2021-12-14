import React, { useContext } from 'react';
import * as actionTypes from '../../../store/actionTypes';
import Context from '../../../store/context';
import classes from './RightContent.module.scss';
import xml2js from 'xml2js';
import { MDBCol, MDBTable, MDBTableBody, MDBTableHead } from 'mdb-react-ui-kit';

const RightContent = ({ films }) => {
  const { actions } = useContext(Context);

  const handleFormat = () => {
    let preparedFilms;

    switch (typeof films) {
      case 'object':
        preparedFilms = films;
        break;
      case 'string':
        preparedFilms = handleXML(films);
        break;
      // case 'csv':
      //   break;
      default:
        return null;
    }

    return printFilms(preparedFilms);
  };

  const handleXML = (films) => {
    const deserialisedFilms = new DOMParser().parseFromString(films, 'application/xml');

    try {
      return xml2js(deserialisedFilms);
    } catch (e) {
      console.error(e);
    }
  };

  const getFilmID = (id) => {
    actions({ type: actionTypes.setFilmID, payload: id });
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
                  <tr key={film.id} onClick={() => getFilmID(film.id)}>
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
      <h1 className={classes.Header}>Output:</h1>
      <ul className={classes.List}>{handleFormat()}</ul>
    </MDBCol>
  );
};

export default RightContent;
