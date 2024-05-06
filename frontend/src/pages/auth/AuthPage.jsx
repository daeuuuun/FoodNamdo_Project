import React, { useState } from "react";
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import LocalPhoneOutlinedIcon from '@mui/icons-material/LocalPhoneOutlined';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import AuthLogo from './components/AuthLogo';
import InputForm from './components/InputForm';

const AuthPage = ({ mode }) => {

    const API_URL = process.env.REACT_APP_API_URL;
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ account_id: '', name: '', phone: '', authNum: '' });

    const onChange = (e) => {
        const { name, value } = e.target;
        if (name === "phone" || name === "authNum") {
            if (value === '' || /^[0-9]+$/.test(value)) {
                setFormData(prevState => ({ ...prevState, [name]: value }));
            }
        } else {
            setFormData(prevState => ({ ...prevState, [name]: value }));
        }
    };

    const isFormValid = () => (
        formData.name && formData.phone && formData.authNum.length == 6 && (mode === 'findPw' ? formData.account_id : true)
    );

    const handleFind = async () => {
        if (mode === 'findId') { // 아이디 찾기
            try {
                const response = await axios.post('http://foodnamdo.iptime.org:8080/', formData);
                console.log(response.data);
                navigate('/login');
            } catch (error) {
                console.log(error);
            }

        } else if (mode === 'findPw') { // 비밀번호 찾기
            try {
                const response = await axios.post('', formData);
                console.log(response.data);
                navigate('/login');
            } catch (error) {
                console.log(error);
            }
        }
    };

    const handleAuth = async () => {
        try {
            const response = await axios.post(
                // `http://localhost:8080/usermanagement/verify?phone=${encodeURIComponent(formData.phone)}`,
                'http://foodnamdoserver.iptime.org:8001/usermanagement/verify', null, {
                params: { phone: formData.phone }
                // 'foodnamdoserver.iptime.org:8001/usermanagement/verify', {
                // phone: formData.phone
            });
            // )
            console.log("response")
            console.log(response);
            // console.log(response.data);
            alert('인증 요청 버튼 눌림');
        } catch (error) {
            console.log(error);
        }
    }


    return (
        <div className="auth-form-container centered-flex">
            <AuthLogo />
            <div className="auth-form">
                {
                    mode === 'findPw' && (
                        <InputForm
                            type="text"
                            name="account_id"
                            placeholder="아이디"
                            icon={<PersonOutlinedIcon className="icons" />}
                            value={formData.account_id}
                            onChange={onChange}
                        />
                    )
                }
                <InputForm
                    type="text"
                    name="name"
                    placeholder="이름"
                    icon={<PersonOutlinedIcon className="icons" />}
                    value={formData.name}
                    onChange={onChange}
                />
                <InputForm
                    type="text"
                    name="phone"
                    placeholder="전화번호 11자리"
                    icon={<LocalPhoneOutlinedIcon className="icons" />}
                    value={formData.phone}
                    onChange={onChange}
                    maxLength={11}
                    buttonText="인증 요청"
                    onButtonClick={handleAuth}
                />
                <InputForm
                    type="text"
                    name="authNum"
                    placeholder="인증번호 6자리 숫자 입력"
                    value={formData.authNum}
                    onChange={onChange}
                    maxLength={6}
                />
                <button
                    className="auth-button"
                    onClick={handleFind}
                    disabled={!isFormValid()}
                    style={{ opacity: isFormValid() ? 1 : 0.5 }}
                >
                    {mode === 'findId' ? '아이디 찾기' : '비밀번호 찾기'}
                </button>
            </div>
        </div>
    );
};

export default AuthPage;