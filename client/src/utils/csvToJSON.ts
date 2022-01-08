import IFilm from '../interfaces/IFilm';

export const monoCSVFilmToJSON = (csv: string): IFilm => {
  const attributes = csv.split(',,');

  return {
    id: parseInt(attributes[0]),
    title: attributes[1],
    year: parseInt(attributes[2]),
    director: attributes[3],
    stars: attributes[4],
    review: attributes[5]
  };
};
