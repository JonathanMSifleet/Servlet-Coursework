const convertJSONFilmToXML = (JSON) => {
  let XML = '<?xml version="1.0" encoding="UTF-8" ?>';
  XML += '<film>';
  XML += '<title>' + JSON.title + '</title>';
  XML += '<year>' + JSON.year + '</year>';
  XML += '<director>' + JSON.director + '</director>';
  XML += '<genre>' + JSON.year + '</genre>';
  XML += '<stars>' + JSON.stars + '</stars>';
  XML += '<review>' + JSON.review + '</review>';
  XML += '</film>';

  console.log('xml: ', XML);

  return XML;
};

export default convertJSONFilmToXML;
