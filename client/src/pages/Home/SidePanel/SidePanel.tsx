/* eslint-disable no-restricted-globals */
import { MDBCol, MDBSpinner, MDBSwitch } from 'mdb-react-ui-kit';
import { ChangeEvent, useEffect, useState } from 'react';
import FormatRadioGroup from '../../../components/RadioGroups/FormatRadioGroup/FormatRadioGroup';
import OperationRadioGroup from '../../../components/RadioGroups/OperationRadioGroup/OperationRadioGroup';
import * as endpoints from '../../../constants/endpoints';
import createFilm, { renderCreateFilmUI } from '../../../crudFunctionality/createFilm';
import deleteFilm, { renderDeleteFilmUI } from '../../../crudFunctionality/deleteFilm';
import getAllFilms, { renderGetAllFilmsUI } from '../../../crudFunctionality/getAllFilms';
import getFilmByID from '../../../crudFunctionality/getFilmsByID';
import getFilmsByTitle, { renderGetFilmsByTitleUI } from '../../../crudFunctionality/getFilmsByTitle';
import updateFilm, { renderUpdateFilmUI } from '../../../crudFunctionality/updateFilm';
import IFilm from '../../../interfaces/IFilm';
import classes from './SidePanel.module.scss';

interface IProps {
  format: string;
  selectedFilmID: number;
  setFilms: (films: IFilm[] | string | null) => void;
  setFormat: (format: string) => void;
  setFormatChanged: (formatChanged: boolean) => void;
  setSelectedFilmID: (selectedFilmID: number | null) => void;
}

const SidePanel: React.FC<IProps> = ({
  format,
  selectedFilmID,
  setFilms,
  setFormat,
  setFormatChanged,
  setSelectedFilmID
}) => {
  const [endpoint, setEndpoint] = useState('');
  const [fontReady, setFontReady] = useState(false);
  const [formData, setFormData] = useState(null as IFilm | null);
  const [searchByTitleVal, setSearchByTitleVal] = useState('');
  const [selectedAttributeVal, setSelectedAttributeVal] = useState(null as unknown as number | string);
  const [selectedFilm, setSelectedFilm] = useState(null as IFilm | null);
  const [selectedLabel, setSelectedLabel] = useState('Title');
  const [shouldDeleteFilm, setShouldDeleteFilm] = useState(false);
  const [shouldGetAllFilms, setShouldGetAllFilms] = useState(false);
  const [shouldGetFilmByID, setShouldGetFilmByID] = useState(false);
  const [shouldGetFilmByTitle, setShouldGetFilmByTitle] = useState(false);
  const [shouldCreateFilm, setShouldCreateFilm] = useState(false);
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
      case 'updateForm':
        setUpdateFormData({ ...selectedFilm, [inputName]: event.target.value! } as IFilm );
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

      setSelectedFilm(await getFilmByID(endpoints.GET_FILM_BY_ID, format, selectedFilmID!, useREST));

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

      setShouldCreateFilm(false);
      setShowSpinner(false);
    };

    if (shouldCreateFilm) postFilm();
  }, [shouldCreateFilm]);

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

      await deleteFilm(endpoint, format, selectedFilmID, useREST);

      setShowSpinner(false);
      setShouldDeleteFilm(false);
    };

    if (shouldDeleteFilm) deleteFilmByID();
  }, [shouldDeleteFilm]);

  const renderSwitch = (): JSX.Element | null => {
    switch (endpoint) {
      case endpoints.GET_ALL_FILMS:
        return renderGetAllFilmsUI((): void => setShouldGetAllFilms(true));
      case endpoints.GET_FILM_BY_TITLE:
        return renderGetFilmsByTitleUI(
          (): void => {
            // @ts-expect-error value does exist on event.target
            setSearchByTitleVal(event!.target!.value!);
          },
          (): void => setShouldGetFilmByTitle(true),
          searchByTitleVal
        );
      case endpoints.CREATE_FILM:
        return renderCreateFilmUI(
          (): void => {
            formChangedHandler(
              event as unknown as ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
              // @ts-expect-error name does exist on event.target
              event!.target!.name!,
              'filmForm'
            );
          },
          (): void => setShouldCreateFilm(true)
        );
      case endpoints.UPDATE_FILM:
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
      case endpoints.DELETE_FILM:
        return renderDeleteFilmUI((): void => setShouldDeleteFilm(true), selectedFilmID!);
      default:
        return null;
    }
  };

  return (
    <>
      {fontReady ? (
        <>
          <MDBCol size="md-3" className={classes.SidePanel}>
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
                // @ts-expect-error event does exist on event.target
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
        </>
      ) : null}
    </>
  );
};

export default SidePanel;
