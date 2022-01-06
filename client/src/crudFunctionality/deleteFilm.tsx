import Button from '../components/Button/Button';
import generateURL from '../utils/generateURL';
import { jsonRequest } from '../utils/requests';

const deleteFilm = async (endpoint: string, format: string, useREST: boolean) => {
  let url = generateURL(endpoint, format, useREST);

  try {
    await jsonRequest(url, 'DELETE');
  } catch (e) {
    console.error(e);
  }
};

export const renderDeleteFilmUI = (onClick: () => void, selectedFilmID: number) => {
  return (
    <>
      {selectedFilmID ? (
        <>
          <p>
            <b>Film ID:</b> {selectedFilmID}
          </p>
          <Button onClick={onClick} text={'Delete film'} />
        </>
      ) : null}
    </>
  );
};

export default deleteFilm;
