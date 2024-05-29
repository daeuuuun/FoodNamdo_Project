import React, { useState, useEffect } from "react";
import '../../styles/authform.css'
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

import { BACKEND_SERVER_URL } from "../../config/Config";
import { defaultBackInstance } from "../../utils/axiosInstance";

const StyledLink = styled(Link)({
	fontSize: '0.9rem',
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
	const [code, setCode] = useState('');
	const [showPassword, setShowPassword] = useState(false);

	const { account_id, password, name, nickname, phone } = signUpForm;

	const [isIdAvailable, setIsIdAvailable] = useState(false); // 사용 가능
	const [isCheckedId, setIsCheckedId] = useState(false); // 중복 확인

	const [isNicknameAvailable, setIsNicknameAvailable] = useState(false);
	const [isCheckedNickname, setIsCheckedNickname] = useState(false);

	const [isVerified, setIsVerified] = useState(false);

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
		}
	}

	const onChangeCode = e => {
		const value = e.target.value;
		if (value === '' || (/^[0-9]+$/.test(value) && value.length <= 6)) {
			setCode(value);
		}
	}

	const checkIdDuplicate = async () => {
		if (account_id) {
			try {
				const response = await defaultBackInstance.get(
					BACKEND_SERVER_URL + '/usermanagement/IdDuplication',
					{
						params: { account_id: account_id }
					}
				);

				if (!response.data) {
					setIsIdAvailable(true);
					setIsCheckedId(true);
					alert('사용가능한 아이디입니다.');
				} else {
					setIsIdAvailable(false);
					setIsCheckedId(false);
					alert('중복된 아이디입니다.');
					setSignUpForm({
						...signUpForm,
						account_id: ''
					});
				}
			} catch (error) {
				console.error(error);
			}
		} else {
			alert('아이디를 입력해주세요.');
		}
	}

	const checkNicknameDuplicate = async () => {
		if (nickname) {
			try {
				const response = await defaultBackInstance.get(
					BACKEND_SERVER_URL + '/usermanagement/NicknameDuplication',
					{
						params: { nickname: nickname }
					}
				);

				if (!response.data) {
					setIsNicknameAvailable(true);
					setIsCheckedNickname(true);
					alert('사용가능한 닉네임입니다.');
				} else {
					setIsNicknameAvailable(false);
					setIsCheckedNickname(false);
					alert('중복된 닉네임입니다.');
					setSignUpForm({
						...signUpForm,
						nickname: ''
					});
				}
			} catch (error) {
				console.error(error);
			}
		} else {
			alert('닉네임을 입력해주세요.');
		}
	}


	const handleAuth = async () => {
		if (!phone) {
			alert('전화번호를 입력해주세요.');
			return;
		};
		try {
			await defaultBackInstance.post(
				BACKEND_SERVER_URL + '/usermanagement/verify', {
				phone: phone
			});
			setIsVerified(true);
			alert('인증번호가 전송되었습니다.');
		} catch (error) {
			alert('인증번호 발송에 실패하였습니다. 다시 시도해주세요.');
			console.log(error);
		}
	};

	const handleSignUp = async () => {
		if (!isCheckedId) {
			alert("아이디 중복 확인을 해주세요.");
		} else if (!password) {
			alert("비밀번호를 입력해주세요.");
		} else if (!name) {
			alert("이름을 입력해주세요.");
		} else if (!isCheckedNickname) {
			alert("닉네임 중복 확인을 해주세요.");
		} else if (!isVerified) {
			alert("전화번호 인증을 해주세요.");
		} else if (!code) {
			alert("인증번호를 입력해주세요.");
		}

		try {
			const response = await defaultBackInstance.post
				(BACKEND_SERVER_URL + '/usermanagement/signUp', {
					accountId: account_id,
					password: password,
					name: name,
					nickname: nickname,
					phone: phone
				});
			console.log(signUpForm);
			setSignUpForm({
				account_id: '',
				password: '',
				name: '',
				nickname: '',
				phone: '',
			})
			setCode('');
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
					<div className="btn" onClick={checkIdDuplicate}>
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
					<div className="btn" onClick={checkNicknameDuplicate}>
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
					<div className="btn" onClick={handleAuth}>
						인증요청
					</div>
				</div>
				<div className={`input-form ${focused === 'code' ? 'input-focus-form' : ''}`}>
					<input
						type="text"
						placeholder="인증번호 6자리 숫자 입력"
						name="code"
						value={code}
						onChange={onChangeCode}
						onFocus={() => setFocused('code')}
						onBlur={() => setFocused(null)}
						maxLength={6}
					/>
				</div>
				<button
					className="auth-button"
					onClick={handleSignUp}
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