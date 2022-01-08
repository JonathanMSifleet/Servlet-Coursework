import * as endpoints from '../constants/endpoints';

const generateURL = (endpoint: string, format: string, useREST: boolean): string => {
  return useREST ? `${endpoints.rest}?format=${format}` : `${endpoint}?format=${format}`;
};

export default generateURL;
