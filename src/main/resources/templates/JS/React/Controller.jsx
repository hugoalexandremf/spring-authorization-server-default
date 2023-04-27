import React from 'react';
import ReactLoading from 'react-loading';
import { MegaphoneIcon, XMarkIcon } from '@heroicons/react/24/outline';

import { Example } from './SpringReactLaboratory/Hooks';
import Navbar from './SpringReactLaboratory/Navbar';
import Homepage from './SpringReactLaboratory/Homepage';

import './css/app.css';

class Controller extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: 0,
    };
  }

  render() {
    if (this.state.loading === 1) {
      return (
        <div>
          <div id='CDQ_loading'>
            <ReactLoading
              type={'spinningBubbles'}
              color={'#507AAA'}
              height={200}
              width={200}
            />
          </div>
        </div>
      );
    } else if (this.state.loading === 0) {
      return (
        <>
          <Navbar />
          <Homepage />
        </>
      );
    }
  }
}

export default Controller;
