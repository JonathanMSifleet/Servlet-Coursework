import { Parser as json2csv } from 'json2csv';
import jsontoxml from 'jsontoxml';
import Button from '../components/Button/Button';
import Input from '../components/Input/Input';
import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';

const createFilm = async (endpoint: string, format: string, formData: IFilm, useREST: boolean) => {
  const url = generateURL(endpoint, format, useREST);

  try {
    switch (format) {
      case 'xml':
        await xmlRequest(url, 'POST', `<Film>${jsontoxml(formData)}</Film>`);
        break;
      case 'csv':
        await csvRequest(
          url,
          'POST',
          new json2csv({ header: false, delimiter: ',,' }).parse(formData!)
        );
        break;
      default:
        await jsonRequest(url, 'POST', formData);
        break;
    }
  } catch (e) {
    console.error(e);
  }
};

export const renderCreateFilmUI = (formChangedHandler: () => void, onClick: () => void) => {
  return (
    <>
      <h3>Film attributes:</h3>

      <form
        onSubmit={(event): void => {
          event.preventDefault();
        }}
      >
        <Input label="Title" name="title" onChange={formChangedHandler} />
        <Input label="Year" name="year" onChange={formChangedHandler} />
        <Input label="Director" name="director" onChange={formChangedHandler} />
        <Input label="Stars" name="stars" onChange={formChangedHandler} />
        <Input label="Review" name="review" onChange={formChangedHandler} />

        <Button onClick={onClick} text={'Create new film'} />
      </form>
    </>
  );
};

export default createFilm;
