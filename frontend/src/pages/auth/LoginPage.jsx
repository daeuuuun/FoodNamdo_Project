import React, { useContext, useState } from 'react';
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import { styled } from '@mui/system';
import AuthLogo from './components/AuthLogo';
import palette from '../../styles/palette';
import { useNavigate } from 'react-router-dom';
import { BACKEND_SERVER_URL } from '../../config/Config';
import { defaultBackInstance } from "../../utils/axiosInstance";
import { AppContext } from '../../utils/loginContext';

const StyledLink = styled(Link)({
    fontSize: '0.9rem',
    fontFamily: 'Gmarket Sans Medium',
    color: `${palette.darygray}`,
    marginRight: '16px',
})

const LoginPage = () => {

    const { login } = useContext(AppContext);

    const navigate = useNavigate();
    const [focused, setFocused] = useState(null);

    const [loginForm, setLoginForm] = useState({
        account_id: '',
        password: '',
    });

    const [isLoginAttempted, setIsLoginAttempted] = useState(false);

    const { account_id, password } = loginForm;

    const onChange = (e) => {
        const userLoginForm = {
            ...loginForm,
            [e.target.name]: e.target.value
        };
        setLoginForm(userLoginForm);
    }

    const handleLogin = async () => {
        setIsLoginAttempted(true);

        try {
            const response = await defaultBackInstance.post(BACKEND_SERVER_URL + '/usermanagement/login', {
                accountId: account_id,
                password: password
            });

            const responseData = response.data;

            localStorage.setItem('accessToken', responseData.accessToken);
            localStorage.setItem('refreshToken', responseData.refreshToken);
            login();
            navigate('/');
        } catch (error) {
            alert('아이디나 비밀번호가 잘못되었습니다. 다시 시도해주세요.')
            console.log(error);
        }
    }


    const onKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleLogin();
        }
    }

    return (
        <div className='auth-form-container centered-flex'>
            <AuthLogo />
            <div className="auth-form">
                <div className={`input-form ${focused === 'account_id' ? 'input-focus-form' : ''}`}>
                    <PersonOutlinedIcon className="icons" />
                    <input
                        type="text"
                        placeholder="아이디"
                        name='account_id'
                        value={account_id}
                        onChange={onChange}
                        onFocus={() => setFocused('account_id')}
                        onBlur={() => setFocused(null)}
                        autoFocus
                    />
                </div>
                <div className={`input-form ${focused === 'password' ? 'input-focus-form' : ''}`}>
                    <LockOutlinedIcon className="icons" />
                    <input
                        type="password"
                        placeholder="비밀번호"
                        name='password'
                        value={password}
                        onChange={onChange}
                        onKeyDown={onKeyDown}
                        onFocus={() => setFocused('password')}
                        onBlur={() => setFocused(null)}
                    />
                </div>
                {isLoginAttempted && (
                    <div className='error-text'>
                        {!account_id && <div>아이디를 입력해 주세요.</div>}
                        {!password && <div>비밀번호를 입력해 주세요.</div>}
                    </div>
                )}
                <div className="auth-button" onClick={handleLogin}>
                    로그인
                </div>
            </div>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    justifyContent: 'center',
                    typography: 'body1',
                }}
            >
                <StyledLink href="/find-id" underline="hover">
                    {'아이디 찾기'}
                </StyledLink>
                <StyledLink href="/find-pw" underline="hover">
                    {'비밀번호 찾기'}
                </StyledLink>
                <StyledLink href="/signup" underline="hover">
                    {'회원가입'}
                </StyledLink>
            </Box>
        </div>
    );
}

export default LoginPage;
