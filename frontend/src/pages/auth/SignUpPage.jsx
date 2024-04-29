import React, { useState } from "react";
import palette from "../../styles/palette";
import PersonIcon from '@mui/icons-material/Person';
import LockIcon from '@mui/icons-material/Lock';
import PhoneIcon from '@mui/icons-material/Phone';
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

    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [nickname, setNickname] = useState('');
    const [phoneNum, setPhoneNum] = useState('');
    const [showPassword, setShowPassword] = useState(false);


    const onClickSignUp = async () => {
        try {
            const response = await axios.post("https://localhost:8080", {
                username: id,
                password: password,
                name: name,
                phone: phoneNum,
            });
            console.log(response.data);
            alert("푸드남도 회원가입을 축하합니다!");
            navigate('/');
        } catch (error) {
            console.error(error);
        }
    };

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    }

    return (
        <div className="auth-form-container centered-flex">
            <AuthLogo />
            <div className="auth-form">
                <div className="input-form">
                    <PersonIcon className="icons" />
                    <input
                        type="text"
                        value={id}
                        placeholder="아이디"
                        required
                        onChange={(e) => setId(e.target.value)}
                    />
                    <div className="btn">
                        중복체크
                    </div>
                </div>
                <div className="input-form">
                    <LockIcon className="icons" />
                    <input
                        type={showPassword ? "text" : "password"}
                        value={password}
                        placeholder="비밀번호"
                        required
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {showPassword ? (
                        <VisibilityOutlinedIcon
                            className="icons"
                            onClick={toggleShowPassword}
                            style={{ color: `${palette.lightblue}` }}
                        />
                    ) : (
                        <VisibilityOffOutlinedIcon
                            className="icons"
                            onClick={toggleShowPassword}
                            style={{ color: `${palette.lightblue}` }}
                        />
                    )}
                </div>
                <div className="input-form">
                    <PersonIcon className="icons" />
                    <input
                        type="text"
                        value={name}
                        placeholder="이름"
                        required
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <div className="input-form">
                    <PersonIcon className="icons" />
                    <input
                        type="text"
                        value={nickname}
                        placeholder="닉네임"
                        required
                        onChange={(e) => setNickname(e.target.value)}
                    />
                </div>
                <div className="input-form">
                    <PhoneIcon className="icons" />
                    <input
                        type="text"
                        value={phoneNum}
                        placeholder="전화번호"
                        required
                        onChange={(e) => setPhoneNum(e.target.value)}
                    />
                    <div className="btn">
                        인증요청
                    </div>
                </div>
                <div className="auth-button" onClick={onClickSignUp}>
                    회원가입
                </div>
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