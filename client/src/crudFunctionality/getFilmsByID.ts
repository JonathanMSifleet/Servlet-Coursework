import { XMLParser as xmlToJSON } from 'fast-xml-parser';
import IFilm from '../interfaces/IFilm';
import { monoCSVToJSON as csvToJSON } from '../utils/csvToJSON';
import generateURL from '../utils/generateURL';
import { jsonRequest, textRequest } from '../utils/requests';

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
        const xmlFilm = await textRequest(url, 'GET');
        return new xmlToJSON().parse(xmlFilm).root.film;
      case 'csv':
        return csvToJSON(await textRequest(url, 'GET'));
      default:
        // @ts-expect-error film is nested
        return (await jsonRequest(url, 'GET'))[0] as IFilm;
    }
  } catch (e) {
    console.error(e);
    return null;
  }
};

export default getFilmByID;
