import React, { useContext, useState, useEffect } from 'react';
import Context from '../../../store/context';
import classes from './LeftContent.module.scss';
import * as actionTypes from '../../../store/actionTypes';
import JSONRequest from '../../../utils/JSONRequest';
import * as endpoints from '../../../endpoints';
import Radio from './../../../components/Radio/Radio';
import { MDBBtn, MDBBtnGroup, MDBCol, MDBSpinner, MDBSwitch } from 'mdb-react-ui-kit';
import Input from './../../../components/Input/Input';
import XMLRequest from '../../../utils/XMLRequest';

const LeftContent = () => {
  const { globalState, actions } = useContext(Context);

  const [endpoint, setEndpoint] = useState('');
  const [error, setError] = useState();
  const [formData, setFormData] = useState({});
  const [format, setFormat] = useState('json');
  const [selectedAttributeVal, setSelectedAttributeVal] = useState();
  const [selectedFilm, setSelectedFilm] = useState();
  const [selectedLabel, setSelectedLabel] = useState('Title');
  const [shouldDeleteFilm, setShouldDeleteFilm] = useState(false);
  const [shouldGetFilmByID, setShouldGetFilmByID] = useState(false);
  const [shouldGetFilmByTitle, setShouldGetFilmByTitle] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);
  const [shouldUpdateFilm, setShouldUpdateFilm] = useState(false);
  const [showSpinner, setShowSpinner] = useState(false);
  const [updateFormData, setUpdateFormData] = useState(false);
  const [useREST, setUseREST] = useState(false);

  useEffect(() => {
    console.log(useREST);
  }, [useREST]);

  const formChangedHandler = (event, inputName, form) => {
    switch (form) {
      case 'filmForm':
        setFormData({
          ...formData,
          [inputName]: event.target.value
        });
        break;
      case 'updateForm':
        setUpdateFormData({
          ...selectedFilm,
          [inputName]: event.target.value
        });
        break;
    }
  };

  const toggleHandler = () => {
    setUseREST(!useREST);
  };

  // create new film
  useEffect(() => {
    const postFilm = async () => {
      try {
        await JSONRequest(endpoints.insertFilmEndpoint, 'POST', formData);
      } catch (e) {
        setError(e);
      }

      setShouldPostFilm(false);
    };

    if (shouldPostFilm) postFilm();
  }, [shouldPostFilm]);

  // get film by title
  useEffect(() => {
    const getFilms = async () => {
      setShowSpinner(true);
      const url = `${endpoint}?format=${format}&title=${formData.filmTitle}`;

      switch (format) {
        case 'json':
          actions({
            type: actionTypes.setFilms,
            payload: await JSONRequest(url, 'GET')
          });
          break;
        case 'xml':
          actions({
            type: actionTypes.setFilms,
            payload: await getXMLFilms(url)
          });
          break;
        // case 'csv':
        //   films = getCSVFilms(url);
        //   break;
        default:
          actions({
            type: actionTypes.setFilms,
            payload: await JSONRequest(url, 'GET')
          });
      }
      setShouldGetFilmByTitle(false);
      setShowSpinner(false);
    };

    if (shouldGetFilmByTitle) getFilms();
  }, [shouldGetFilmByTitle]);

  // get film by ID
  useEffect(() => {
    const getFilmByID = async () => {
      const url = `${endpoint}?format=${format}&id=${globalState.filmID}`;

      try {
        const film = await JSONRequest(url, 'GET');
        setSelectedFilm(film[0]);
      } catch (e) {
        setError(e);
      }

      setShouldGetFilmByID(false);
    };

    if (shouldGetFilmByID) getFilmByID();
  }, [shouldGetFilmByID]);

  // update film
  useEffect(() => {
    const updateFilm = async () => {
      const url = `${endpoints.updateFilmEndpoint}?format=${format}`;
      setShowSpinner(true);

      try {
        await JSONRequest(url, 'PUT', updateFormData);
      } catch (e) {
        setError(e);
      }

      setShowSpinner(false);
      setShouldUpdateFilm(false);
    };

    if (shouldUpdateFilm) updateFilm();
  }, [shouldUpdateFilm]);

  // delete film
  useEffect(() => {
    const deleteFilm = async () => {
      const url = `${endpoints.deleteFilmEndpoint}?format=${format}&id=${globalState.filmID}`;
      setShowSpinner(true);

      try {
        await JSONRequest(url, 'DELETE');
      } catch (e) {
        setError(e);
      }

      setShowSpinner(false);
      setShouldDeleteFilm(false);
    };

    if (shouldDeleteFilm) deleteFilm();
  }, [shouldDeleteFilm]);

  const getXMLFilms = async (url) => {
    try {
      return await XMLRequest(url);
    } catch (e) {
      setError(e);
      return null;
    }
  };

  const handleSelectChange = (event) => {
    setSelectedAttributeVal(event.target.value);
    setSelectedLabel(event.target.selectedOptions[0].innerText);
  };

  const renderSwitch = () => {
    switch (endpoint) {
      case endpoints.getAllFilmsEndpoint:
        return (
          <MDBBtn onClick={() => setShouldGetFilmByTitle(true)}>
            Get films
          </MDBBtn>
        );
      case endpoints.getFilmByTitleEndpoint:
        return (
          <>
            <Input
              label="Title"
              onChange={(event) => {
                formChangedHandler(event, 'filmTitle', 'filmForm');
              }}
            />
            <MDBBtn onClick={() => setShouldGetFilmByTitle(true)}>Get film(s)</MDBBtn>
          </>
        );
      case endpoints.insertFilmEndpoint:
        return (
          <>
            <h3>Film attributes:</h3>

            <form
              onSubmit={(event) => {
                event.preventDefault();
              }}
            >
              <Input
                className={classes.Input}
                label="Title"
                onChange={(event) => formChangedHandler(event, 'title', 'filmForm')}
              />
              <Input
                className={classes.Input}
                label="Year"
                onChange={(event) => formChangedHandler(event, 'year', 'filmForm')}
              />
              <Input
                className={classes.Input}
                label="Director"
                onChange={(event) => formChangedHandler(event, 'director', 'filmForm')}
              />
              <Input
                className={classes.Input}
                label="Stars"
                onChange={(event) => formChangedHandler(event, 'stars', 'filmForm')}
              />
              <Input
                className={classes.Input}
                label="Review"
                onChange={(event) => formChangedHandler(event, 'review', 'filmForm')}
              />

              <MDBBtn onClick={() => setShouldPostFilm(true)}>Create new film</MDBBtn>
            </form>
          </>
        );
      // actually the update method
      case endpoints.getFilmByIDEndpoint:
        return (
          <>
            {selectedFilm ? (
              <>
                <p>
                  <b>Film:</b> {selectedFilm.title}
                </p>

                <div className={classes.SelectWrapper}>
                  <select
                    className={classes.Select}
                    name="filmAttributes"
                    onChange={(event) => handleSelectChange(event)}
                  >
                    <option value={selectedFilm.title}>Title</option>
                    <option value={selectedFilm.year}>Year</option>
                    <option value={selectedFilm.director}>Director</option>
                    <option value={selectedFilm.stars}>Stars</option>
                    <option value={selectedFilm.review}>Review</option>
                  </select>

                  {selectedLabel !== 'Review' ? (
                    <input
                      className={classes.SelectInput}
                      label={selectedLabel ? selectedLabel : 'Title'}
                      placeholder={selectedAttributeVal ? selectedAttributeVal : selectedFilm.title}
                      onChange={(event) => formChangedHandler(event, selectedLabel.toLowerCase(), 'updateForm')}
                      type="text"
                    />
                  ) : (
                    <textarea
                      className={`${classes.SelectInput} ${classes.ReviewInput}`}
                      label={selectedLabel ? selectedLabel : 'Title'}
                      placeholder={selectedAttributeVal ? selectedAttributeVal : selectedFilm.title}
                      onChange={(event) => formChangedHandler(event, selectedLabel.toLowerCase(), 'updateForm')}
                      type="text"
                    />
                  )}
                </div>
                <MDBBtn onClick={() => setShouldUpdateFilm(true)}>Update film</MDBBtn>
              </>
            ) : null}
            {!selectedFilm && globalState.filmID ? (
              <>
                <p>
                  Selected film ID:
                  {globalState.filmID}
                </p>
                <MDBBtn onClick={() => setShouldGetFilmByID(true)}>Get film data</MDBBtn>
              </>
            ) : null}
            {!selectedFilm && !globalState.filmID ? (
              <p>
                In order to update a film, you must click a film&apos;s title, which can be retrieved via getting all
                films, or a film by its a title
              </p>
            ) : null}
          </>
        );
      case endpoints.deleteFilmEndpoint:
        console.log('selectedFilm', selectedFilm);
        return (
          <>
            {globalState.filmID ? (
              <>
                <p>
                  <b>Film ID:</b> {globalState.filmID}
                </p>

                <MDBBtn onClick={() => setShouldDeleteFilm(true)}>Delete film</MDBBtn>
              </>
            ) : null}
          </>
        );
    }
  };

  return (
    <MDBCol size="md-3" className={classes.LeftContent}>
      <h3>Format: </h3>
      <MDBSwitch defaultChecked label="Use REST servlet" onChange={() => toggleHandler()} checked={useREST} />

      <MDBBtnGroup className={classes.FormatRadioGroup}>
        <Radio defaultChecked label="JSON" name="formatGroup" onClick={() => setFormat('json')} />
        <Radio label="XML" name="formatGroup" onClick={() => setFormat('xml')} />
        <Radio label="Text" name="formatGroup" onClick={() => setFormat('csv')} />
      </MDBBtnGroup>

      <MDBBtnGroup className={classes.OperationRadioGroup}>
        <Radio name="operationGroup" label="Get all films" onClick={() => setEndpoint(endpoints.getAllFilmsEndpoint)} />
        <Radio
          name="operationGroup"
          label="Get film by title"
          onClick={() => setEndpoint(endpoints.getFilmByTitleEndpoint)}
        />
        <Radio name="operationGroup" label="Add new film" onClick={() => setEndpoint(endpoints.insertFilmEndpoint)} />
        <Radio name="operationGroup" label="Update film" onClick={() => setEndpoint(endpoints.getFilmByIDEndpoint)} />
        <Radio name="operationGroup" label="Delete film" onClick={() => setEndpoint(endpoints.deleteFilmEndpoint)} />
      </MDBBtnGroup>

      {renderSwitch()}

      {showSpinner ? (
        <div className="d-flex justify-content-center">
          <MDBSpinner role="status" />
        </div>
      ) : null}

      {error ? <p>Error occured: {error}</p> : null}
    </MDBCol>
  );
};

export default LeftContent;
