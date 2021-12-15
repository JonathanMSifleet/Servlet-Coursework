const CSVRequest = async (url, method, body) => {
  let response = await fetch(url, {
    method,
    body
  });

  return await response.text();
};

export default CSVRequest;
