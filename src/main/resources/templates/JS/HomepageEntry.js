import React from 'react';
import ReactDOM from 'react-dom';
import {
     BrowserRouter as Router
} from 'react-router-dom';
import Controller from './React/Controller';

ReactDOM.render(
     <Router>
          <Controller/>
     </Router>,
     document.getElementById('root')
);