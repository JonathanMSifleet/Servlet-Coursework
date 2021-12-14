// must load before App
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import React from 'react';
import App from './App';
import Context from './store/context';
import ReactDOM from 'react-dom';
import StateHook from './store/StateHook';
import reportWebVitals from './reportWebVitals';
import './styles/global.scss';

const AppWithState = () => {
  const store = StateHook();

  return (
    <Context.Provider value={store}>
      <App />
    </Context.Provider>
  );
};

ReactDOM.render(
  <React.StrictMode>
    <AppWithState />
  </React.StrictMode>,
  document.getElementById('app')
);

reportWebVitals();
