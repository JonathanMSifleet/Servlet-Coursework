import React, { useContext } from 'react';
import classes from './RightContent.module.scss';
import { MDBCol, MDBTable, MDBTableBody, MDBTableHead } from 'mdb-react-ui-kit';
import Context from '../../../store/context';
import * as actionTypes from '../../../store/actionTypes';

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
      return convertXMLtoJSON(deserialisedFilms);
    } catch (e) {
      console.error(e);
    }
  };

  const convertXMLtoJSON = (xml) => {
    const json = [];

    const localXML = xml.getElementsByTagName('root')[0].children;

    for (let i = 0; i < localXML.length; i++) {
      const child = localXML[i];

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

  const getFilmID = (id) => {
    actions({ type: actionTypes.setFilmID, payload: id });
  };

  const printFilms = (preparedFilms) => {
    return (
      <MDBTable striped>
        <MDBTableHead>
        <tr>
          <th scope='col'>ID</th>
          <th scope='col'>Title</th>
          <th scope='col'>Year</th>
          <th scope='col'>Director</th>
          <th scope='col'>Stars</th>
          <th scope='col'>Review</th>
        </tr>
      </MDBTableHead>
      <MDBTableBody>
        {preparedFilms
          ? preparedFilms.map((film) => {
              return (
                <tr onClick={() => getFilmID(film.id)}>
                    <td>{film.id}</td>
                    <td>{film.title}</td>
                    <td>{film.year}</td>
                    <td>{film.director}</td>
                    <td>{film.stars}</td>
                    <td>{film.review}</td>
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
