import React from 'react';
import './index.css';
import App from './App';

import {createRoot} from 'react-dom/client';
import {AppProvider} from "./utils/loginContext";


createRoot(document.getElementById('root')).render(
    <AppProvider>
        <App />
    </AppProvider>
);
