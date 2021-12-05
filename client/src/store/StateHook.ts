import { useState } from 'react';
import * as actionTypes from './actionTypes';

interface IPayload {
  test: unknown;
}

const StateHook = (): {
  globalState: IPayload;
  actions: any;
} => {
  const [test, setTest] = useState(null as unknown as boolean | null);

  const actions = (action: { type: string; payload?: IPayload }) => {
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
