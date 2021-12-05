import { useState } from 'react';
import * as actionTypes from './actionTypes';

const StateHook = ()=> {
  const [test, setTest] = useState(null);

  const actions = (action) => {
    const { type, payload } = action;

    switch (type) {
      case actionTypes.test:
        return () => {
          setTest(null);
        };
    }
  };

  const globalState = {
    test
  };

  return { globalState, actions };
};

export default StateHook;
