import React, { useState, useEffect } from "react";
import palette from "../../styles/palette";
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import LocalPhoneOutlinedIcon from '@mui/icons-material/LocalPhoneOutlined';

import VisibilityOutlinedIcon from '@mui/icons-material/VisibilityOutlined';
import VisibilityOffOutlinedIcon from '@mui/icons-material/VisibilityOffOutlined';

import { useNavigate } from 'react-router-dom';
import axios from "axios";

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import { styled } from '@mui/system';
import AuthLogo from './components/AuthLogo';

const StyledLink = styled(Link)({
    fontSize: '0.75rem',
    fontFamily: 'Gmarket Sans Medium',
    color: `${palette.darygray}`,
    marginRight: '16px',
})

const SignUpPage = () => {
    const navigate = useNavigate();
    const [focused, setFocused] = useState(null);
    const [signUpForm, setSignUpForm] = useState({
        account_id: '',
        password: '',
        name: '',
        nickname: '',
        phone: '',
    });
    const [authNum, setAuthNum] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const { account_id, password, name, nickname, phone } = signUpForm;

    const onChange = e => {
        const userSignUpForm = {
            ...signUpForm,
            [e.target.name]: e.target.value
        };
        setSignUpForm(userSignUpForm);
    }

    const onChangePhone = e => {
        const value = e.target.value;
        if (value === '' || /^[0-9]+$/.test(value)) {
            setSignUpForm({
                ...signUpForm,
                phone: value
            });
        } else {
            alert('숫자를 입력해 주세요.');
        }
    }

    const onChangeAuthNum = e => {
        const value = e.target.value;
        if (value === '' || (/^[0-9]+$/.test(value) && value.length <= 6)) {
            setAuthNum(value);
        } else {
            alert('인증번호는 6자리 숫자여야 합니다.');
        }
    }

    const handleSignUp = async () => {
        try {
            // const response = await axios.post("https://localhost:8080", signUpForm);
            console.log(signUpForm);
            setSignUpForm({
                account_id: '',
                password: '',
                name: '',
                nickname: '',
                phone: '',
            })
            alert("푸드남도 회원가입을 축하합니다!");
            navigate('/');
        } catch (error) {
            console.error(error);
        }
    };

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    }

    // 모든 필드가 채워져 있는지 확인하는 함수
    const isFormValid = () => {
        return (
            account_id && password && name && nickname && phone && authNum.length === 6
        );
    };

    return (
        <div className="auth-form-container centered-flex">
            <AuthLogo />
            <div className="auth-form">
                <div className={`input-form ${focused === 'account_id' ? 'input-focus-form' : ''}`}>
                    <PersonOutlinedIcon className="icons" />
                    <input
                        type="text"
                        placeholder="아이디"
                        name="account_id"
                        value={account_id}
                        onChange={onChange}
                        onFocus={() => setFocused('account_id')}
                        onBlur={() => setFocused(null)}
                        autoFocus
                    />
                    <div className="btn">
                        중복체크
                    </div>
                </div>
                <div className={`input-form ${focused === 'password' ? 'input-focus-form' : ''}`}>
                    <LockOutlinedIcon className="icons" />
                    <input
                        type={showPassword ? "text" : "password"}
                        placeholder="비밀번호"
                        name="password"
                        value={password}
                        onChange={onChange}
                        onFocus={() => setFocused('password')}
                        onBlur={() => setFocused(null)}
                    />
                    {showPassword ? (
                        <VisibilityOutlinedIcon
                            className="icons"
                            onClick={toggleShowPassword}
                            style={{ color: `${palette.blue}` }}
                        />
                    ) : (
                        <VisibilityOffOutlinedIcon
                            className="icons"
                            onClick={toggleShowPassword}
                            style={{ color: `${palette.blue}` }}
                        />
                    )}
                </div>
                <div className={`input-form ${focused === 'name' ? 'input-focus-form' : ''}`}>
                    <PersonOutlinedIcon className="icons" />
                    <input
                        type="text"
                        placeholder="이름"
                        name="name"
                        value={name}
                        onChange={onChange}
                        onFocus={() => setFocused('name')}
                        onBlur={() => setFocused(null)}
                    />
                </div>
                <div className={`input-form ${focused === 'nickname' ? 'input-focus-form' : ''}`}>
                    <PersonOutlinedIcon className="icons" />
                    <input
                        type="text"
                        placeholder="닉네임"
                        name="nickname"
                        value={nickname}
                        onChange={onChange}
                        onFocus={() => setFocused('nickname')}
                        onBlur={() => setFocused(null)}
                    />
                    <div className="btn">
                        중복체크
                    </div>
                </div>
                <div className={`input-form ${focused === 'phone' ? 'input-focus-form' : ''}`}>
                    <LocalPhoneOutlinedIcon className="icons" />
                    <input
                        type="text"
                        placeholder="전화번호 11자리"
                        name="phone"
                        value={phone}
                        onChange={onChangePhone}
                        onFocus={() => setFocused('phone')}
                        onBlur={() => setFocused(null)}
                        maxLength={11}
                    />
                    <div className="btn">
                        인증요청
                    </div>
                </div>
                <div className={`input-form ${focused === 'authNum' ? 'input-focus-form' : ''}`}>
                    <input
                        type="text"
                        placeholder="인증번호 6자리 숫자 입력"
                        name="authNum"
                        value={authNum}
                        onChange={onChangeAuthNum}
                        onFocus={() => setFocused('authNum')}
                        onBlur={() => setFocused(null)}
                        maxLength={6}
                    />
                </div>
                <button
                    className="auth-button"
                    onClick={handleSignUp}
                    disabled={!isFormValid()}
                    style={{ opacity: isFormValid() ? 1 : 0.5 }}
                >
                    회원가입
                </button>
            </div>
            <div>
                <Box
                    sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        justifyContent: 'center',
                        typography: 'body1',
                    }}
                >
                    <StyledLink href="/login" underline="hover">
                        {'로그인 하러가기'}
                    </StyledLink>
                </Box>
            </div>
        </div >
    );
}

export default SignUpPage;