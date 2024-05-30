import React, { createContext, useState } from 'react';
const AppContext = createContext();

const AppProvider = ({ children }) => {
    const [state, setState] = useState({
        isAuthenticated: !!localStorage.getItem('accessToken'),
    });

    const login = () => {
        setState({ isAuthenticated: true });
    };

    const logout = () => {
        setState({ isAuthenticated: false });
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    };

    return (
        <AppContext.Provider value={{ isAuthenticated: state.isAuthenticated, login, logout }}>
            {children}
        </AppContext.Provider>
    );
};

export { AppContext, AppProvider };
