// must load before App
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import '@fontsource/roboto';
import React from 'react';
import App from './App';
import ReactDOM from 'react-dom';
import './styles/global.scss';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('app')
);
