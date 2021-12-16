import React, { useState, useEffect } from 'react';
import * as endpoints from '../../../constants/endpoints';
import CSVRequest from '../../../utils/requests/CSVRequest';
import Input from '../../../components/Input/Input';
import JSONRequest from '../../../utils/requests/JSONRequest';
import Radio from '../../../components/Radio/Radio';
import XMLRequest from '../../../utils/requests/XMLRequest';
import classes from './ControlPanel.module.scss';
import jsontoxml from 'jsontoxml';
import singleXMLFilmToJSON from '../../../utils/singleXMLFilmToJSON';
import { MDBBtn, MDBBtnGroup, MDBCol, MDBSpinner, MDBSwitch } from 'mdb-react-ui-kit';
import Output from './Output/Output';

const ControlPanel = () => {
  const [endpoint, setEndpoint] = useState('');
  const [films, setFilms] = useState(null);
  const [formData, setFormData] = useState({});
  const [format, setFormat] = useState('json');
  const [formatChanged, setFormatChanged] = useState(false);
  const [selectedAttributeVal, setSelectedAttributeVal] = useState();
  const [selectedFilm, setSelectedFilm] = useState();
  const [selectedFilmID, setSelectedFilmID] = useState(null);
  const [selectedLabel, setSelectedLabel] = useState('Title');
  const [shouldDeleteFilm, setShouldDeleteFilm] = useState(false);
  const [shouldGetAllFilms, setShouldGetAllFilms] = useState(false);
  const [shouldGetFilmByID, setShouldGetFilmByID] = useState(false);
  const [shouldGetFilmByTitle, setShouldGetFilmByTitle] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);
  const [shouldUpdateFilm, setShouldUpdateFilm] = useState(false);
  const [showSpinner, setShowSpinner] = useState(false);
  const [updateFormData, setUpdateFormData] = useState(false);
  const [useREST, setUseREST] = useState(false);

  // reset films on format change:
  useEffect(() => {
    setFilms(null);
    setFormatChanged(null);
  }, [format]);

  const sharedSetSelectedFilmID = (id) => {
    console.log('🚀 ~ file: ControlPanel.jsx ~ line 41 ~ sharedSetSelectedFilmID ~ id', id);
    setSelectedFilmID(id);
  };

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

  // get all films
  useEffect(() => {
    async function getFilms() {
      try {
        const url = `${endpoints.getAllFilmsEndpoint}?format=${format}`;
        setShowSpinner(true);

        switch (format) {
          case 'xml':
            setFilms(await XMLRequest(url, 'GET'));
            break;
          case 'csv':
            setFilms(await CSVRequest(url, 'GET'));
            break;
          default:
            setFilms(await JSONRequest(url, 'GET'));
        }
      } catch (e) {
        console.error(e);
      }

      setShowSpinner(false);
      setShouldGetAllFilms(false);
    }

    if (shouldGetAllFilms) getFilms();
  }, [shouldGetAllFilms]);

  // get film by title
  useEffect(() => {
    const getFilms = async () => {
      if (!formData.title) return;

      setShowSpinner(true);
      const url = `${endpoint}?format=${format}&title=${formData.title}`;

      try {
        switch (format) {
          case 'xml':
            setFilms(await XMLRequest(url, 'GET'));
            break;
          case 'csv':
            setFilms(await CSVRequest(url, 'GET'));
            break;
          default:
            setFilms(await JSONRequest(url, 'GET'));
        }
      } catch (e) {
        console.error(e);
      }

      setShowSpinner(false);
      setShouldGetFilmByTitle(false);
    };

    if (shouldGetFilmByTitle) getFilms();
  }, [shouldGetFilmByTitle]);

  // get film by ID
  useEffect(() => {
    const getFilmByID = async () => {
      const url = `${endpoint}?format=${format}&id=${selectedFilmID}`;
      setShowSpinner(true);

      let film;
      try {
        switch (format) {
          case 'json':
            film = await JSONRequest(url, 'GET');
            break;
          case 'xml':
            const response = await XMLRequest(url, 'GET');
            film = singleXMLFilmToJSON(response);
            break;
        }
        setSelectedFilm(film);
      } catch (e) {
        console.error(e);
      }

      setShowSpinner(false);
      setShouldGetFilmByID(false);
    };

    if (shouldGetFilmByID) getFilmByID();
  }, [shouldGetFilmByID]);

  // create new film
  useEffect(() => {
    async function postFilm() {
      const url = `${endpoint}?format=${format}`;
      setShowSpinner(true);

      try {
        switch (format) {
          case 'json':
            await JSONRequest(url, 'POST', formData);
            break;
          case 'xml':
            await XMLRequest(url, 'POST', `<film>${jsontoxml(formData)}</film>`);
            break;
          // case 'csv':
          //   films = insertCSVFilm(url);
          //   break;
        }
      } catch (e) {
        console.error(e);
      }

      setShouldPostFilm(false);
      setShowSpinner(false);
    }

    if (shouldPostFilm) postFilm();
  }, [shouldPostFilm]);

  // update film
  useEffect(() => {
    const updateFilm = async () => {
      const url = `${endpoints.updateFilmEndpoint}?format=${format}`;
      setShowSpinner(true);

      try {
        switch (format) {
          case 'json':
            await JSONRequest(url, 'PUT', updateFormData);
            break;
          case 'xml':
            const film = jsontoxml(updateFormData);
            await XMLRequest(url, 'PUT', `<film>${film}</film>`);
            break;
          // case 'csv':
          //   films = insertCSVFilm(url);
          //   break;
        }
      } catch (e) {
        console.error(e);
      }

      setUpdateFormData(null);
      setShowSpinner(false);
      setShouldUpdateFilm(false);
      setSelectedFilm(null);
    };

    if (shouldUpdateFilm) updateFilm();
  }, [shouldUpdateFilm]);

  // delete film
  useEffect(() => {
    const deleteFilm = async () => {
      const url = `${endpoints.deleteFilmEndpoint}?format=${format}&id=${selectedFilmID}`;
      setShowSpinner(true);

      try {
        await JSONRequest(url, 'DELETE');
      } catch (e) {
        console.error(e);
      }

      setShowSpinner(false);
      setShouldDeleteFilm(false);
    };

    if (shouldDeleteFilm) deleteFilm();
  }, [shouldDeleteFilm]);

  const handleSelectChange = (event) => {
    setSelectedAttributeVal(event.target.value);
    setSelectedLabel(event.target.selectedOptions[0].innerText);
  };

  const renderSwitch = () => {
    switch (endpoint) {
      case endpoints.getAllFilmsEndpoint:
        return <MDBBtn onClick={() => setShouldGetAllFilms(true)}>Get films</MDBBtn>;
      case endpoints.getFilmByTitleEndpoint:
        return (
          <>
            <Input
              label="Title"
              onChange={(event) => {
                formChangedHandler(event, 'title', 'filmForm');
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
              <Input className={classes.Input} label="Title" onChange={(event) => formChangedHandler(event, 'title', 'filmForm')} />
              <Input className={classes.Input} label="Year" onChange={(event) => formChangedHandler(event, 'year', 'filmForm')} />
              <Input className={classes.Input} label="Director" onChange={(event) => formChangedHandler(event, 'director', 'filmForm')} />
              <Input className={classes.Input} label="Stars" onChange={(event) => formChangedHandler(event, 'stars', 'filmForm')} />
              <Input className={classes.Input} label="Review" onChange={(event) => formChangedHandler(event, 'review', 'filmForm')} />

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
                  <select className={classes.Select} name="filmAttributes" onChange={(event) => handleSelectChange(event)}>
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
                <MDBBtn className={classes.UpdateFilmButton} onClick={() => setShouldUpdateFilm(true)}>
                  Update film
                </MDBBtn>
              </>
            ) : null}
            {!selectedFilm && selectedFilmID ? (
              <>
                <p>
                  Selected film ID:{''}
                  {selectedFilmID}
                </p>
                <MDBBtn onClick={() => setShouldGetFilmByID(true)}>Get film data</MDBBtn>
              </>
            ) : null}
            {!selectedFilm && !selectedFilmID ? (
              <p>
                In order to update a film, you must click a film&apos;s ID from the table, which can be retrieved via getting all films, or getting a film by
                its title
              </p>
            ) : null}
          </>
        );
      case endpoints.deleteFilmEndpoint:
        return (
          <>
            {selectedFilmID ? (
              <>
                <p>
                  <b>Film ID:</b> {selectedFilmID}
                </p>

                <MDBBtn onClick={() => setShouldDeleteFilm(true)}>Delete film</MDBBtn>
              </>
            ) : null}
          </>
        );
    }
  };

  return (
    <>
      <MDBCol size="md-3" className={classes.LeftContent}>
        <h3>Format: </h3>
        <MDBSwitch className={classes.RESTToggle} label="Use REST servlet" onChange={() => toggleHandler()} checked={useREST} />

        <MDBBtnGroup className={classes.FormatRadioGroup}>
          <Radio
            defaultChecked
            label="JSON"
            name="formatGroup"
            onClick={() => {
              setFormatChanged(true);
              setFormat('json');
            }}
          />
          <Radio
            label="XML"
            name="formatGroup"
            onClick={() => {
              setFormatChanged(true);
              setFormat('xml');
            }}
          />
          <Radio
            label="Text"
            name="formatGroup"
            onClick={() => {
              setFormatChanged(true);
              setFormat('csv');
            }}
          />
        </MDBBtnGroup>

        <MDBBtnGroup className={classes.OperationRadioGroup}>
          <Radio name="operationGroup" label="Get all films" onClick={() => setEndpoint(endpoints.getAllFilmsEndpoint)} />
          <Radio name="operationGroup" label="Get film by title" onClick={() => setEndpoint(endpoints.getFilmByTitleEndpoint)} />
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
      </MDBCol>
      <Output films={films} format={format} formatChanged={formatChanged} sharedSetSelectedFilmID={sharedSetSelectedFilmID} />
    </>
  );
};

export default ControlPanel;