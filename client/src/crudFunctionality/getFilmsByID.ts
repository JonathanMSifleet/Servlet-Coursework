import { XMLParser as xmlToJSON } from 'fast-xml-parser';
import IFilm from '../interfaces/IFilm';
import { monoCSVToJSON as csvToJSON } from '../utils/csvToJSON';
import generateURL from '../utils/generateURL';
import { jsonRequest, textRequest as csvRequest, textRequest as xmlRequest } from '../utils/requests';

const getFilmByID = async (
  endpoint: string,
  format: string,
  selectedFilmID: number,
  useREST: boolean
): Promise<IFilm | null> => {
  let url = generateURL(endpoint, format, useREST);
  url = `${url}&id=${selectedFilmID}`;
  if (useREST) url = `${url}&getType=id`;

  try {
    switch (format) {
      case 'xml':
        return new xmlToJSON().parse(await xmlRequest(url, 'GET')).root.film;
      case 'csv':
        return csvToJSON(await csvRequest(url, 'GET'));
      default:
        return (await jsonRequest(url, 'GET')) as IFilm;
    }
  } catch (e) {
    console.error(e);
    return null;
  }
};

export default getFilmByID;
