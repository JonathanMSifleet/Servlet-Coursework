import { Parser as jsonToCSV } from 'json2csv';
import jsonToXML from 'jsontoxml';
import Button from '../components/Button/Button';
import Input from '../components/Input/Input';
import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { jsonRequest, textRequest } from '../utils/requests';

const createFilm = async (endpoint: string, format: string, formData: IFilm, useREST: boolean): Promise<void> => {
  const url = generateURL(endpoint, format, useREST);

  try {
    switch (format) {
      case 'xml':
        const xmlFilm = `<Film>${jsonToXML(formData)}</Film>`;
        await textRequest(url, 'POST', xmlFilm);
        break;
      case 'csv':
        const csvFilm = new jsonToCSV({ header: false, delimiter: ',,', quote: '' }).parse(formData!);
        await textRequest(url, 'POST', csvFilm);
        break;
      default:
        await jsonRequest(url, 'POST', formData);
    }
  } catch (e) {
    console.error(e);
  }
};

export const renderCreateFilmUI = (formChangedHandler: () => void, onClick: () => void): JSX.Element => {
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
