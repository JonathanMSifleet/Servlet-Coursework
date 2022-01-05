import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';

const getAllFilms = async (
  endpoint: string,
  format: string,
  useREST: boolean
): Promise<string | IFilm[] | null> => {
  let url = generateURL(endpoint, format, useREST);
  if (useREST) url = `${url}&getType=all`;

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

export default getAllFilms;
