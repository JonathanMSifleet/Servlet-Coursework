import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';

const getFilmsByTitle = async (
  endpoint: string,
  format: string,
  searchByTitleVal: string,
  useREST: boolean
): Promise<string | IFilm[] | null> => {
  if (searchByTitleVal === '') return null;

  let url = generateURL(endpoint, format, useREST);
  url = `${url}&title=${searchByTitleVal}`;
  if (useREST) {
    url = `${url}&getType=title`;
  }

  try {
    switch (format) {
      case 'xml':
        return await xmlRequest(url, 'GET');
      case 'csv':
        return await csvRequest(url, 'GET');
      default:
        return (await jsonRequest(url, 'GET')) as IFilm[];
    }
  } catch (e) {
    console.error(e);
    return null;
  }
};

export default getFilmsByTitle;
