import { REST, ROOT } from '../constants/endpoints';

const generateURL = (endpoint: string, format: string, useREST: boolean): string => {
  if (useREST) {
    return `${ROOT}/${REST}?format=${format}`;
  } else {
    return `${ROOT}/${endpoint}?format=${format}`;
  }
};

export default generateURL;
