const XMLRequest = async (url, method, body) => {
  let response = await fetch(url, {
    method: method,
    body
  });

  response = await response.text();

  const xml = new DOMParser().parseFromString(response, 'application/xml');
  return new XMLSerializer().serializeToString(xml.documentElement);
};

export default XMLRequest;
