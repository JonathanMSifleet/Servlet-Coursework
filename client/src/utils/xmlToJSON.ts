import IFilm from '../interfaces/IFilm';

export const monoXMLFilmToJSON = (xmlToParse: string): IFilm => {
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

export const polyXMLFilmToJSON = (xmlToParse: string): IFilm[] => {
  const parsedXML = new DOMParser().parseFromString(xmlToParse, 'application/xml');
  const xmlDocument = parsedXML.getElementsByTagName('root')[0].children;

  const json: IFilm[] = [];

  for (let i = 0; i < xmlDocument.length; i++) {
    const child = xmlDocument[i];

    const id = parseInt(child.getElementsByTagName('id')[0].textContent!);
    const title = child.getElementsByTagName('title')[0].textContent!;
    const year = parseInt(child.getElementsByTagName('year')[0].textContent!);
    const director = child.getElementsByTagName('director')[0].textContent!;
    const stars = child.getElementsByTagName('stars')[0].textContent!;
    const review = child.getElementsByTagName('review')[0].textContent!;

    json.push({ id, title, year, director, stars, review });
  }
  return json;
};
