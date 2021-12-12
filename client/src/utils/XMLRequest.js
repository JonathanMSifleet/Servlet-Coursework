const createXMLRequest = async (url) => {
  let response = await fetch(url, {
    method: 'GET'
  });
  response = await response.text();

  const xml = new DOMParser().parseFromString(response, 'application/xml');
  return new XMLSerializer().serializeToString(xml.documentElement);
};

export default createXMLRequest;
