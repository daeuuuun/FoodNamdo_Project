import { createContext, useContext, useState } from 'react';

const FileContext = createContext(null);

export const useFile = () => useContext(FileContext);

export const FileProvider = ({ children }) => {
    const [file, setFile] = useState(null);

    return (
        <FileContext.Provider value={{ file, setFile }}>
            {children}
        </FileContext.Provider>
    );
};