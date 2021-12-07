import { useState } from 'react';
import * as actionTypes from './actionTypes';

const StateHook = () => {
  const [endpoint, setEndpoint] = useState('');
  const [formData, setFormData] = useState({});
  const [format, setFormat] = useState('json');
  const [shouldGetFilm, setShouldGetFilm] = useState(false);
  const [shouldPostFilm, setShouldPostFilm] = useState(false);

  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.setEndpoint:
        return setEndpoint(payload);
      case actionTypes.setFormat:
        return setFormat(payload);
      case actionTypes.setFormData:
        return setFormData({ ...formData, ...payload });
      case actionTypes.setShouldGetFilm:
        return setShouldGetFilm(payload);
      case actionTypes.setShouldPostFilm:
        return setShouldPostFilm(payload);
      case actionTypes.resetState:
        return () => {
          setEndpoint('');
          setFormData({});
          setFormat('json');
          setShouldGetFilm(false);
          setShouldPostFilm(false);
        };
    }
  };

  const globalState = {
    endpoint,
    formData,
    format,
    shouldGetFilm,
    shouldPostFilm
  };

  return { globalState, actions };
};

export default StateHook;
