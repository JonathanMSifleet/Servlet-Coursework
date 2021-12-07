const createHTTPRequest = async (url, method, body) => {
  let options;

  if (body) {
    options = { method, body: JSON.stringify(body) };
  } else {
    options = { method };
  }

  const response = await fetch(url, options);
  return await response.json();
};

export default createHTTPRequest;
