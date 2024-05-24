import React, { useState } from "react";
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import LocalPhoneOutlinedIcon from '@mui/icons-material/LocalPhoneOutlined';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import AuthLogo from './components/AuthLogo';
import InputForm from './components/InputForm';
import { BACKEND_SERVER_URL } from "../../config/Config";

const AuthPage = ({ mode }) => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ account_id: '', name: '', phone: '', code: '' });

    const [isVerified, setIsVerified] = useState(false); // 인증 확인 요청 

    const onChange = (e) => {
        const { name, value } = e.target;
        if (name === "phone" || name === "code") {
            if (value === '' || /^[0-9]+$/.test(value)) {
                setFormData(prevState => ({ ...prevState, [name]: value }));
            }
        } else {
            setFormData(prevState => ({ ...prevState, [name]: value }));
        }
    };

    const isFormValid = () => (
        formData.name && formData.phone && formData.code.length == 6 && (mode === 'findPw' ? formData.account_id : true)
    );

    const handleFind = async () => {
        if (mode === 'findPw') {
            if (!formData.account_id) {
                alert("아이디를 입력해주세요.");
                return;
            }
        };

        if (!formData.name) {
            alert("이름을 입력해주세요.");
            return;
        } else if (!isVerified) {
            alert("인증번호 요청을 해주세요.");
            return;
        } else if (!formData.code) {
            alert("코드를 입력해주세요.");
            return;
        }

        if (mode === 'findId') { // 아이디 찾기
            try {
                const response = await axios.post
                    (BACKEND_SERVER_URL + '/usermanagement/findAccountIdByNameAndPhone', {}, {
                        params: {
                            name: formData.name,
                            phone: formData.phone,
                            code: formData.code
                        }
                    });
                alert('아이디가 전송되었습니다.');
                navigate('/login');
            } catch (error) {
                console.log(error);
                alert('해당 정보를 찾을 수 없습니다.');
                setFormData(prevState => ({
                    ...prevState,
                    name: '',
                    phone: '',
                    code: '',
                }));
            }
        } else if (mode === 'findPw') { // 비밀번호 찾기
            try {
                const response = await axios.post
                    (BACKEND_SERVER_URL + '/usermanagement/findPasswordByAccountIdAndNameAndPhone', {}, {
                        params: {
                            accountId: formData.account_id,
                            name: formData.name,
                            phone: formData.phone,
                            code: formData.code,
                        }
                    });
                alert('비밀번호가 전송되었습니다.');
                navigate('/login');
            } catch (error) {
                alert('해당 정보를 찾을 수 없습니다.');
                setFormData(prevState => ({
                    ...prevState,
                    account_id: '',
                    name: '',
                    phone: '',
                    code: '',
                }));
            }
        }
    };

    const handleAuth = async () => {
        if (!formData.phone) {
            alert('전화번호를 입력해주세요.');
            return;
        };
        try {
            await axios.post(
                BACKEND_SERVER_URL + '/usermanagement/verify', {}, {
                params: { phone: formData.phone }
            });
            setIsVerified(true);
            alert('인증번호가 전송되었습니다.');
        } catch (error) {
            alert('인증번호 발송에 실패하였습니다. 다시 시도해주세요.');
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
                    name="code"
                    placeholder="인증번호 6자리 숫자 입력"
                    value={formData.code}
                    onChange={onChange}
                    maxLength={6}
                />
                <button
                    className="auth-button"
                    onClick={handleFind}
                >
                    {mode === 'findId' ? '아이디 찾기' : '비밀번호 찾기'}
                </button>
            </div>
        </div>
    );
};

export default AuthPage;