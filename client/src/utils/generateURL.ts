import * as endpoints from '../constants/endpoints';

const generateURL = (endpoint: string, format: string, useREST: boolean): string => {
  const url = 'https://servletcoursework-336513.nw.r.appspot.com';

  if (useREST) {
    return `${url}/${endpoints.REST}?format=${format}`;
  } else {
    return `${url}/${endpoint}?format=${format}`;
  }
};

export default generateURL;
