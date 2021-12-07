import { useState } from 'react';
import * as actionTypes from './actionTypes';

const StateHook = () => {
  const [endpoint, setEndpoint] = useState('');
  const [formData, setFormData] = useState({});
  const [format, setFormat] = useState('json');
  const [films, setFilms] = useState(null);
  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setEndpoint:
        return setEndpoint(payload);
      case actionTypes.setFormat:
        return setFormat(payload);
      case actionTypes.setFormData:
        return setFormData({ ...formData, ...payload });
      case actionTypes.setFilms:
        return setFilms(payload);
      case actionTypes.resetState:
        return () => {
          setEndpoint('');
          setFilm(null);
          setFormData({});
          setFormat('json');
        };
    }
  };

  const globalState = {
    endpoint,
    films,
    formData,
    format
  };

  return { globalState, actions };
};

export default StateHook;
