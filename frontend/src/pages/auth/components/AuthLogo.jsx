import React from 'react';
import { useNavigate } from "react-router-dom";

const AuthLogo = () => {
    const navigate = useNavigate();

    return (
        <div onClick={() => navigate('/')} style={{ cursor: 'pointer' }}>
            <img src={process.env.PUBLIC_URL + '/logo.png'} alt="Logo" />
        </div>
    );
};

export default AuthLogo;