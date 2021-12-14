const CSVRequest = async (url, method, body) => {
  let response = await fetch(url, {
    method,
    body
  });

  response = await response.text();
  console.log('🚀 ~ file: CSVRequest.js ~ line 8 ~ CSVRequest ~ response', response);

  return response;
};

export default CSVRequest;
