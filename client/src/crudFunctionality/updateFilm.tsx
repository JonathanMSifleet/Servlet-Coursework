import { Parser as json2csv } from 'json2csv';
import jsontoxml from 'jsontoxml';
import Button from '../components/Button/Button';
import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';
import classes from './scss/updateFilm.module.scss';

const updateFilm = async (endpoint: string, format: string, updateFormData: IFilm, useREST: boolean): Promise<void> => {
  const url = generateURL(endpoint, format, useREST);

  try {
    switch (format) {
      case 'xml':
        const xmlFilm = jsontoxml(updateFormData);
        await xmlRequest(url, 'PUT', `<Film>${xmlFilm}</Film>`);
        break;
      case 'csv':
        await csvRequest(url, 'PUT', new json2csv({ header: false, delimiter: ',,' }).parse(updateFormData!));
        break;
      default:
        await jsonRequest(url, 'PUT', updateFormData);
    }
  } catch (e) {
    console.error(e);
  }
};

export const renderUpdateFilmUI = (
  formChangedHandler: () => void,
  handleSelectChange: () => void,
  onClickGetByID: () => void,
  onClickUpdate: () => void,
  selectedFilm: IFilm,
  selectedFilmID: number,
  selectedLabel: string,
  selectedAttributeVal: string | number
): JSX.Element => {
  return (
    <>
      {selectedFilm ? (
        <>
          <p>
            <b>Film:</b> {selectedFilm.title}
          </p>

          <div className={classes.SelectWrapper}>
            <select className={classes.Select} name="filmAttributes" onChange={handleSelectChange}>
              <option value={selectedFilm.title}>Title</option>
              <option value={selectedFilm.year}>Year</option>
              <option value={selectedFilm.director}>Director</option>
              <option value={selectedFilm.stars}>Stars</option>
              <option value={selectedFilm.review}>Review</option>
            </select>

            {selectedLabel !== 'Review' ? (
              <input
                className={classes.SelectInput}
                placeholder={selectedAttributeVal ? String(selectedAttributeVal) : selectedFilm.title}
                onChange={formChangedHandler}
                type="text"
              />
            ) : (
              <textarea
                className={`${classes.SelectInput} ${classes.ReviewInput}`}
                placeholder={selectedAttributeVal ? String(selectedAttributeVal) : selectedFilm.title}
                onChange={formChangedHandler}
              />
            )}
          </div>
          <Button onClick={onClickUpdate} text={'Update Film'} />
        </>
      ) : null}
      {!selectedFilm && selectedFilmID ? (
        <>
          <p>
            Selected film ID:
            {' ' + selectedFilmID}
          </p>
          <Button onClick={onClickGetByID} text={'Get film data'} />
        </>
      ) : null}
      {!selectedFilm && !selectedFilmID ? (
        <p>
          In order to update a film, you must click a film&apos;s ID from the table, which can be retrieved via getting
          all films, or getting a film by its title
        </p>
      ) : null}
    </>
  );
};

export default updateFilm;
