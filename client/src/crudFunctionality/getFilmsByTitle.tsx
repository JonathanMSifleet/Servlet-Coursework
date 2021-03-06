import Button from '../components/Button/Button';
import Input from '../components/Input/Input';
import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { jsonRequest, textRequest } from '../utils/requests';

const getFilmsByTitle = async (
  endpoint: string,
  format: string,
  searchByTitleVal: string,
  useREST: boolean
): Promise<string | IFilm[] | null> => {
  if (searchByTitleVal === '') return null;

  let url = generateURL(endpoint, format, useREST);
  url = `${url}&title=${searchByTitleVal}`;
  if (useREST) url = `${url}&getType=title`;

  try {
    switch (format) {
      case 'xml':
        return await textRequest(url, 'GET');
      case 'csv':
        return await textRequest(url, 'GET');
      default:
        return (await jsonRequest(url, 'GET')) as IFilm[];
    }
  } catch (e) {
    console.error(e);
    return null;
  }
};

export const renderGetFilmsByTitleUI = (
  formChangedHandler: () => void,
  onClick: () => void,
  searchByTitleVal: string
): JSX.Element => {
  return (
    <>
      <Input label="Title" onChange={formChangedHandler} value={searchByTitleVal} />
      <Button onClick={onClick} text={'Get film(s)'} />
    </>
  );
};

export default getFilmsByTitle;
