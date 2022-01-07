/* eslint-disable no-restricted-globals */
import { MDBCol, MDBContainer, MDBRow, MDBSpinner, MDBSwitch } from 'mdb-react-ui-kit';
import { ChangeEvent, useEffect, useState } from 'react';
import FormatRadioGroup from '../../components/RadioGroups/FormatRadioGroup/FormatRadioGroup';
import OperationRadioGroup from '../../components/RadioGroups/OperationRadioGroup/OperationRadioGroup';
import * as endpoints from '../../constants/endpoints';
import createFilm, { renderCreateFilmUI } from '../../crudFunctionality/createFilm';
import deleteFilm, { renderDeleteFilmUI } from '../../crudFunctionality/deleteFilm';
import getAllFilms, { renderGetAllFilmsUI } from '../../crudFunctionality/getAllFIlms';
import getFilmByID, { renderGetFilmsByIDUI } from '../../crudFunctionality/getFilmsByID';
import getFilmsByTitle from '../../crudFunctionality/getFilmsByTitle';
import updateFilm, { renderUpdateFilmUI } from '../../crudFunctionality/updateFilm';
import IFilm from '../../interfaces/IFilm';
import classes from './Home.module.scss';
import Output from './Output/Output';

const Home: React.FC = () => {
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

  // function for lifting state up from data component
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

  const handleSelectChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    setSelectedAttributeVal(event.target.value);
    setSelectedLabel(event.target.selectedOptions[0].innerText);
  };

  // get all films
  useEffect(() => {
    const getFilms = async (): Promise<void> => {
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

      await updateFilm(endpoints.updateFilm, format, updateFormData!, useREST);

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
        return renderGetAllFilmsUI((): void => setShouldGetAllFilms(true));
      case endpoints.getFilmByTitle:
        return renderGetFilmsByIDUI(
          (): void => {
            formChangedHandler(
              event as unknown as ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
              'title',
              'searchByTitleForm'
            );
          },
          (): void => setShouldGetFilmByTitle(true),
          searchByTitleVal
        );
      case endpoints.insertFilm:
        return renderCreateFilmUI(
          (): void => {
            formChangedHandler(
              event as unknown as ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
              // @ts-expect-error name does exist on event.target
              event!.target!.name!,
              'filmForm'
            );
          },
          (): void => setShouldPostFilm(true)
        );
      // actually the update method
      case endpoints.getFilmByID:
        return renderUpdateFilmUI(
          (): void =>
            formChangedHandler(
              event as unknown as ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
              selectedLabel.toLowerCase(),
              'updateForm'
            ),
          (): void => handleSelectChange(event as unknown as ChangeEvent<HTMLSelectElement>),
          (): void => setShouldGetFilmByID(true),
          (): void => setShouldUpdateFilm(true),
          selectedFilm!,
          selectedFilmID!,
          selectedLabel,
          selectedAttributeVal
        );
      case endpoints.deleteFilm:
        return renderDeleteFilmUI((): void => setShouldDeleteFilm(true), selectedFilmID!);
      default:
        return null;
    }
  };

  // only render if font has loaded
  return (
    <MDBContainer className={classes.PageWrapper}>
      <MDBRow className={classes.PageContent}>
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

              <FormatRadioGroup
                onClick={(): void => {
                  setFormatChanged(true);
                  // @ts-expect-error id does exist on event.target
                  setFormat(event!.target!.id);
                }}
              />

              <OperationRadioGroup
                onClick={(): void => {
                  // @ts-expect-error
                  setEndpoint(endpoints[event!.target!.id]);
                }}
              />

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
      </MDBRow>
    </MDBContainer>
  );
};

export default Home;
