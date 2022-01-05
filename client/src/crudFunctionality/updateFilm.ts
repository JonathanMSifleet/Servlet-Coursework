import { Parser as json2csv } from 'json2csv';
import jsontoxml from 'jsontoxml';
import IFilm from '../interfaces/IFilm';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';

const updateFilm = async (
  endpoint: string,
  format: string,
  updateFormData: IFilm,
  useREST: boolean
) => {
  let url = generateURL(endpoint, format, useREST);

  try {
    switch (format) {
      case 'xml':
        const xmlFilm = jsontoxml(updateFormData);
        await xmlRequest(url, 'PUT', `<Film>${xmlFilm}</Film>`);
        break;
      case 'csv':
        await csvRequest(
          url,
          'PUT',
          new json2csv({ header: false, delimiter: ',,' }).parse(updateFormData!)
        );
        break;
      default:
        await jsonRequest(url, 'PUT', updateFormData);
        break;
    }
  } catch (e) {
    console.error(e);
  }
};

export default updateFilm;
