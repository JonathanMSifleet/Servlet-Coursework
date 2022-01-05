import generateURL from '../utils/generateURL';
import { jsonRequest } from '../utils/requests';

const deleteFilm = async (endpoint: string, format: string, useREST: boolean) => {
  let url = generateURL(endpoint, format, useREST);

  try {
    await jsonRequest(url, 'DELETE');
  } catch (e) {
    console.error(e);
  }
};

export default deleteFilm;
