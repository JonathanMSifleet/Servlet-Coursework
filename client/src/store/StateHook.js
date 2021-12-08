import { useState } from 'react';
import * as actionTypes from './actionTypes';

const StateHook = () => {
  const [films, setFilms] = useState(null);
  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setFilms:
        return setFilms(payload);
    }
  };

  const globalState = {
    films
  };

  return { globalState, actions };
};

export default StateHook;
