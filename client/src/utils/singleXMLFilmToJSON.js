const singleXMLFilmToJSON = (xml) => {
  xml = new DOMParser().parseFromString(xml, 'application/xml');
  xml = xml.getElementsByTagName('film')[0].children;

  const id = xml[0].innerHTML;
  const title = xml[1].innerHTML;
  const year = xml[2].innerHTML;
  const director = xml[3].innerHTML;
  const stars = xml[4].innerHTML;
  const review = xml[5].innerHTML;

  return { id, title, year, director, stars, review };
};

export default singleXMLFilmToJSON;
