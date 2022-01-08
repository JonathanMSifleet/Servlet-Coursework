import Button from '../components/Button/Button';
import Input from '../components/Input/Input';
import IFilm from '../interfaces/IFilm';
import csvToJSON from '../utils/csvToJSON';
import generateURL from '../utils/generateURL';
import { csvRequest, jsonRequest, xmlRequest } from '../utils/requests';
import xmlToJSON from '../utils/xmlToJSON';

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
        const xmlResponse = await xmlRequest(url, 'GET');
        return xmlToJSON(xmlResponse);
      case 'csv':
        const csvResponse = await csvRequest(url, 'GET');
        return csvToJSON(csvResponse);
      default:
        return (await jsonRequest(url, 'GET')) as IFilm;
    }
  } catch (e) {
    console.error(e);
    return null;
  }
};

export const renderGetFilmsByIDUI = (
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

export default getFilmByID;
