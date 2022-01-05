import * as endpoints from '../constants/endpoints';

const generateURL = (endpoint: string, format: string, useREST: boolean): string => {
  if (useREST) {
    return `${endpoints.rest}?format=${format}`;
  }

  return `${endpoint}?format=${format}`;
};

export default generateURL;
