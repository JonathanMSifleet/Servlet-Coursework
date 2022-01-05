import { Parser as json2csv } from 'json2csv';
import jsontoxml from 'jsontoxml';
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

export default createFilm;
