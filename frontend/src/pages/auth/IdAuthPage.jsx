import React, { useState } from "react";
import PersonIcon from '@mui/icons-material/Person';
import PhoneIcon from '@mui/icons-material/Phone';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import AuthLogo from './components/AuthLogo';



const IdAuthPage = () => {
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [phoneNum, setPhoneNum] = useState('');
    const [authNum, setAuthNum] = useState('');

    const onClickAuto = async () => {
        try {
            const response = await axios.post("https://localhost:8080/member", {

            });
            console.log(response.data);
            navigate('/login');
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <div className="auth-form-container centered-flex">
            <AuthLogo />
            <div className="auth-form">
                <div className="input-form">
                    <PersonIcon className="icons" />
                    <input
                        type="text"
                        value={name}
                        placeholder="이름"
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <div className="input-form">
                    <PhoneIcon className="icons" />
                    <input
                        type="text"
                        value={phoneNum}
                        placeholder="전화번호"
                        onChange={(e) => setPhoneNum(e.target.value)}
                    />
                    <div className="btn">
                        인증요청
                    </div>
                </div>
                <div className="input-form">
                    <input
                        type="text"
                        value={authNum}
                        placeholder="인증번호"
                        onChange={(e) => setAuthNum(e.target.value)}
                    />
                </div>
                <div
                    className="auth-button"
                    onClick={onClickAuto}
                >
                    인증확인
                </div>
            </div>
        </div >
    );
}

export default IdAuthPage;