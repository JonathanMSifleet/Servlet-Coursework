import { MDBBtn, MDBBtnGroup, MDBCol, MDBSpinner, MDBSwitch } from 'mdb-react-ui-kit';
import React, { useEffect, useState } from 'react';
import Input from '../../../components/Input/Input';
import Radio from '../../../components/Radio/Radio';
import * as endpoints from '../../../constants/endpoints';
import createFilm from '../../../crudFunctionality/createFilm';
import deleteFilm from '../../../crudFunctionality/deleteFilm';
import getAllFilms from '../../../crudFunctionality/getAllFIlms';
import getFilmByID from '../../../crudFunctionality/getFilmByID';
import getFilmsByTitle from '../../../crudFunctionality/getFilmsByTitle';
import updateFilm from '../../../crudFunctionality/updateFilm';
import IFilm from '../../../interfaces/IFilm';
import classes from './ControlPanel.module.scss';
import Output from './Output/Output';

const ControlPanel: React.FC = () => {
  const [endpoint, setEndpoint] = useState('');
  const [films, setFilms] = useState(null as IFilm[] | string | null);
  const [fontReady, setFontReady] = useState(false);
  const [formData, setFormData] = useState(null as IFilm | null);
  const [format, setFormat] = useState('json');
  const [formatChanged, setFormatChanged] = useState(false);
  const [searchByTitleVal, setSearchByTitleVal] = useState('');
  const [selectedAttributeVal, setSelectedAttributeVal] = useState(
    null as unknown as number | string
  );
  const [selectedFilm, setSelectedFilm] = useState(null as IFilm | null);
  const [selectedFilmID, setSelectedFilmID] = useState(null as number | null);
  const [selectedLabel, setSelectedLabel] = useState('Title');
  const [shouldDeleteFilm, setShouldDeleteFilm] = useState(false);
  const [shouldGetAllFilms, setShouldGetAllFilms] = useState(false);
  const [shouldGetFilmByID, setShouldGetFilmByID] = useState(false);
  const [shouldGetFilmByTitle, setShouldGetFilmByTitle] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);
  const [shouldUpdateFilm, setShouldUpdateFilm] = useState(false);
  const [showSpinner, setShowSpinner] = useState(false);
  const [updateFormData, setUpdateFormData] = useState(null as IFilm | null);
  const [useREST, setUseREST] = useState(false);

  // Require font to load before rendering
  useEffect(() => {
    document.fonts.load('1rem "Roboto"').then(() => {
      setFontReady(true);
    });
  }, []);

  // reset state data on format change or REST toggle:
  useEffect(() => {
    setFilms(null);
    setFormatChanged(false);
    setSelectedFilm(null);
    setSelectedFilmID(null);
    setSearchByTitleVal('');
  }, [format, useREST]);

  const sharedSetSelectedFilmID = (id: number): void => {
    setSelectedFilmID(id);
  };

  const formChangedHandler = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    inputName: string,
    form: string
  ): void => {
    switch (form) {
      case 'filmForm':
        setFormData({
          ...formData!,
          [inputName]: event.target.value!
        });
        break;
      case 'searchByTitleForm':
        setSearchByTitleVal(event.target.value!);
        break;
      case 'updateForm':
        setUpdateFormData({
          ...selectedFilm!,
          [inputName]: event.target.value!
        });
        break;
    }
  };

  const toggleHandler = (): void => {
    setUseREST(!useREST);
  };

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    setSelectedAttributeVal(event.target.value);
    setSelectedLabel(event.target.selectedOptions[0].innerText);
  };

  // get all films
  useEffect(() => {
    const getFilms = async () => {
      setShowSpinner(true);

      setFilms(await getAllFilms(endpoint, format, useREST));

      setShowSpinner(false);
      setShouldGetAllFilms(false);
    };
    if (shouldGetAllFilms) getFilms();
  }, [shouldGetAllFilms]);

  // get film by title
  useEffect(() => {
    const getFilms = async (): Promise<void> => {
      setShowSpinner(true);

      setFilms(await getFilmsByTitle(endpoint, format, searchByTitleVal, useREST));

      setFormData(null);
      setSearchByTitleVal('');
      setShowSpinner(false);
      setShouldGetFilmByTitle(false);
    };

    if (shouldGetFilmByTitle) getFilms();
  }, [shouldGetFilmByTitle]);

  // get film by ID
  useEffect(() => {
    const getFilm = async (): Promise<void> => {
      setShowSpinner(true);
      setSelectedFilm(await getFilmByID(endpoint, format, selectedFilmID!, useREST));

      setShowSpinner(false);
      setShouldGetFilmByID(false);
    };

    if (shouldGetFilmByID) getFilm();
  }, [shouldGetFilmByID]);

  // create new film
  useEffect(() => {
    const postFilm = async (): Promise<void> => {
      setShowSpinner(true);

      await createFilm(endpoint, format, formData!, useREST);

      setShouldPostFilm(false);
      setShowSpinner(false);
    };

    if (shouldPostFilm) postFilm();
  }, [shouldPostFilm]);

  // update film
  useEffect(() => {
    const putFilm = async (): Promise<void> => {
      setShowSpinner(true);

      await updateFilm(endpoint, format, updateFormData!, useREST);

      setUpdateFormData(null);
      setShowSpinner(false);
      setShouldUpdateFilm(false);
      setSelectedFilm(null);
    };

    if (shouldUpdateFilm) putFilm();
  }, [shouldUpdateFilm]);

  // delete film
  useEffect(() => {
    const deleteFilmByID = async (): Promise<void> => {
      setShowSpinner(true);

      await deleteFilm(endpoint, format, useREST);

      setShowSpinner(false);
      setShouldDeleteFilm(false);
    };

    if (shouldDeleteFilm) deleteFilmByID();
  }, [shouldDeleteFilm]);

  const renderSwitch = (): JSX.Element | null => {
    switch (endpoint) {
      case endpoints.getAllFilms:
        return <MDBBtn onClick={(): void => setShouldGetAllFilms(true)}>Get films</MDBBtn>;
      case endpoints.getFilmByTitle:
        return (
          <>
            <Input
              label="Title"
              onChange={(event): void => {
                formChangedHandler(event, 'title', 'searchByTitleForm');
              }}
              value={searchByTitleVal}
            />
            <MDBBtn onClick={(): void => setShouldGetFilmByTitle(true)}>Get film(s)</MDBBtn>
          </>
        );
      case endpoints.insertFilm:
        return (
          <>
            <h3>Film attributes:</h3>

            <form
              onSubmit={(event): void => {
                event.preventDefault();
              }}
            >
              <Input
                label="Title"
                onChange={(event): void => formChangedHandler(event, 'title', 'filmForm')}
              />
              <Input
                label="Year"
                onChange={(event): void => formChangedHandler(event, 'year', 'filmForm')}
              />
              <Input
                label="Director"
                onChange={(event): void => formChangedHandler(event, 'director', 'filmForm')}
              />
              <Input
                label="Stars"
                onChange={(event): void => formChangedHandler(event, 'stars', 'filmForm')}
              />
              <Input
                label="Review"
                onChange={(event): void => formChangedHandler(event, 'review', 'filmForm')}
              />

              <MDBBtn onClick={(): void => setShouldPostFilm(true)}>Create new film</MDBBtn>
            </form>
          </>
        );
      // actually the update method
      case endpoints.getFilmByID:
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
                    onChange={(event): void => handleSelectChange(event)}
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
                      placeholder={
                        selectedAttributeVal ? String(selectedAttributeVal) : selectedFilm.title
                      }
                      onChange={(event): void =>
                        formChangedHandler(event, selectedLabel.toLowerCase(), 'updateForm')
                      }
                      type="text"
                    />
                  ) : (
                    <textarea
                      className={`${classes.SelectInput} ${classes.ReviewInput}`}
                      placeholder={
                        selectedAttributeVal ? String(selectedAttributeVal) : selectedFilm.title
                      }
                      onChange={(event): void =>
                        formChangedHandler(event, selectedLabel.toLowerCase(), 'updateForm')
                      }
                    />
                  )}
                </div>
                <MDBBtn
                  className={classes.UpdateFilmButton}
                  onClick={(): void => setShouldUpdateFilm(true)}
                >
                  Update film
                </MDBBtn>
              </>
            ) : null}
            {!selectedFilm && selectedFilmID ? (
              <>
                <p>
                  Selected film ID:
                  {' ' + selectedFilmID}
                </p>
                <MDBBtn onClick={(): void => setShouldGetFilmByID(true)}>Get film data</MDBBtn>
              </>
            ) : null}
            {!selectedFilm && !selectedFilmID ? (
              <p>
                In order to update a film, you must click a film&apos;s ID from the table, which can
                be retrieved via getting all films, or getting a film by its title
              </p>
            ) : null}
          </>
        );
      case endpoints.deleteFilm:
        return (
          <>
            {selectedFilmID ? (
              <>
                <p>
                  <b>Film ID:</b> {selectedFilmID}
                </p>
                <MDBBtn onClick={(): void => setShouldDeleteFilm(true)}>Delete film</MDBBtn>
              </>
            ) : null}
          </>
        );
      default:
        return null;
    }
  };

  // only render if font has loaded
  return (
    <>
      {fontReady ? (
        <>
          <MDBCol size="md-3" className={classes.LeftContent}>
            <h3>Format: </h3>
            <MDBSwitch
              className={classes.RESTToggle}
              label="Use REST servlet"
              onChange={(): void => toggleHandler()}
              checked={useREST}
            />

            <MDBBtnGroup className={classes.FormatRadioGroup}>
              <Radio
                defaultChecked
                label="JSON"
                name="formatGroup"
                onClick={(): void => {
                  setFormatChanged(true);
                  setFormat('json');
                }}
              />
              <Radio
                label="XML"
                name="formatGroup"
                onClick={(): void => {
                  setFormatChanged(true);
                  setFormat('xml');
                }}
              />
              <Radio
                label="Text"
                name="formatGroup"
                onClick={(): void => {
                  setFormatChanged(true);
                  setFormat('csv');
                }}
              />
            </MDBBtnGroup>

            <MDBBtnGroup className={classes.OperationRadioGroup}>
              <Radio
                className={classes.TopOperationRadio}
                label="Get all films"
                name="operationGroup"
                onClick={(): void => setEndpoint(endpoints.getAllFilms)}
              />
              <Radio
                className={classes.TopOperationRadio}
                label="Get film by title"
                name="operationGroup"
                onClick={(): void => setEndpoint(endpoints.getFilmByTitle)}
              />
              <Radio
                className={classes.TopOperationRadio}
                label="Add new film"
                name="operationGroup"
                onClick={(): void => setEndpoint(endpoints.insertFilm)}
              />
              <Radio
                className={classes.BottomOperationRadio}
                label="Update film"
                name="operationGroup"
                onClick={(): void => setEndpoint(endpoints.getFilmByID)}
              />
              <Radio
                className={classes.BottomOperationRadio}
                label="Delete film"
                name="operationGroup"
                onClick={(): void => setEndpoint(endpoints.deleteFilm)}
              />
            </MDBBtnGroup>

            {renderSwitch()}

            {showSpinner ? (
              <div className="d-flex justify-content-center">
                <MDBSpinner role="status" />
              </div>
            ) : null}
          </MDBCol>
          <Output
            films={films as IFilm[]}
            format={format}
            formatChanged={formatChanged}
            sharedSetSelectedFilmID={sharedSetSelectedFilmID}
          />
        </>
      ) : null}
    </>
  );
};

export default ControlPanel;
