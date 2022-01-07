import IFilm from '../interfaces/IFilm';

export const jsonRequest = async (
  url: string,
  method: string,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  body?: any
): Promise<IFilm[] | IFilm> => {
  let options;

  if (body) {
    options = { method, body: JSON.stringify(body) };
  } else {
    options = { method };
  }

  const response = await fetch(url, options);
  return await response.json();
};

export const xmlRequest = async (url: string, method: string, body?: string): Promise<string> => {
  const response = await fetch(url, {
    method,
    body
  });

  const responseBody = await response.text();

  const xml = new DOMParser().parseFromString(responseBody, 'application/xml');
  return new XMLSerializer().serializeToString(xml.documentElement);
};

export const csvRequest = async (url: string, method: string, body?: string): Promise<string> => {
  const response = await fetch(url, {
    method,
    body
  });

  return await response.text();
};
