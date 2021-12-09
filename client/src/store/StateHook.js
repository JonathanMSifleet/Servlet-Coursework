import { useState } from 'react';
import * as actionTypes from './actionTypes';

const StateHook = () => {
  const [films, setFilms] = useState(null);
  const [filmID, setFilmID] = useState(null);

  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setFilms:
        return setFilms(payload);
      case actionTypes.setFilmID:
        return setFilmID(payload);
    }
  };

  const globalState = {
    films,
    filmID
  };

  return { globalState, actions };
};

export default StateHook;
