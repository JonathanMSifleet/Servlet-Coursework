// must load before App
import '@fontsource/roboto';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import { StrictMode } from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './styles/global.scss';

ReactDOM.render(
  <StrictMode>
    <App />
  </StrictMode>,
  document.getElementById('app')
);
