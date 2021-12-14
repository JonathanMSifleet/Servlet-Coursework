import * as actionTypes from './actionTypes';
import { useState } from 'react';

const StateHook = () => {
  const [filmID, setFilmID] = useState(null);
  const [films, setFilms] = useState(null);

  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setFilmID:
        return setFilmID(payload);
      case actionTypes.setFilms:
        return setFilms(payload);
    }
  };

  const globalState = {
    filmID,
    films
  };

  return { globalState, actions };
};

export default StateHook;
