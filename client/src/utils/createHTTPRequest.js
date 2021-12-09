const createHTTPRequest = async (url, method, body, mode) => {
  let options;

  if (body) {
    options = { method, body: JSON.stringify(body), mode };
  } else {
    options = { method };
  }

  const response = await fetch(url, options);
  return await response.json();
};

export default createHTTPRequest;
