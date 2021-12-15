import * as actionTypes from './actionTypes';
import { useState } from 'react';

const StateHook = () => {
  const [filmID, setFilmID] = useState(null);
  const [films, setFilms] = useState(null);
  const [filmFormat, setFilmFormat] = useState(null);

  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setFilmID:
        return setFilmID(payload);
      case actionTypes.setFilms:
        return setFilms(payload);
      case actionTypes.setFilmFormat:
        return setFilmFormat(payload);
    }
  };

  const globalState = {
    filmID,
    films,
    filmFormat
  };

  return { globalState, actions };
};

export default StateHook;
