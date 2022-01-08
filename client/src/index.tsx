// must load before App
import '@fontsource/roboto';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import { StrictMode } from 'react';
import { render } from 'react-dom';
import Home from './pages/Home/Home';
import './styles/global.scss';

render(
  <StrictMode>
    <Home />
  </StrictMode>,
  document.getElementById('app')
);
