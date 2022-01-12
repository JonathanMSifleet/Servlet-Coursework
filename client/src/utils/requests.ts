import IFilm from '../interfaces/IFilm';

export const jsonRequest = async (url: string, method: string, body?: IFilm | IFilm[]): Promise<IFilm[] | IFilm> => {
  const options = body ? { method, body: JSON.stringify(body) } : { method };

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
