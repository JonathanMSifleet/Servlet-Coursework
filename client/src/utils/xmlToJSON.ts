import IFilm from '../interfaces/IFilm';

const xmlToJSON = (xmlToParse: string): IFilm => {
  const parsedXML = new DOMParser().parseFromString(xmlToParse, 'application/xml');
  const xmlDocument = parsedXML.getElementsByTagName('film')[0].children;

  const id = parseInt(xmlDocument[0].innerHTML);
  const title = xmlDocument[1].innerHTML;
  const year = parseInt(xmlDocument[2].innerHTML);
  const director = xmlDocument[3].innerHTML;
  const stars = xmlDocument[4].innerHTML;
  const review = xmlDocument[5].innerHTML;

  return { id, title, year, director, stars, review };
};

export default xmlToJSON;
