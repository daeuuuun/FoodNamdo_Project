import React, { useState } from 'react';
import AuthLogo from './components/AuthLogo';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useLocation, useNavigate } from 'react-router-dom';
import { BACKEND_SERVER_URL } from '../../config/Config';
import axios from 'axios';

const ResetPwPage = () => {

	const { state } = useLocation();
	const navigate = useNavigate();
	const [focused, setFocused] = useState(null);
	const [pwMatch, setPwMatch] = useState(true);

	const [resetForm, setResetForm] = useState({
		new_password: '',
		new_password_check: ''
	})

	const { new_password, new_password_check } = resetForm;

	const onChange = (e) => {
		const { name, value } = e.target;
		setResetForm(prevState => ({
			...prevState,
			[name]: value
		}));

		if (name === 'new_password_check') {
			setPwMatch(new_password === value);
		}
	}

	const handleResetPw = async () => {
		try {
			await axios.post(BACKEND_SERVER_URL + 'usermanagement/changePassword', {
				userId: state,
				newPassword: new_password
			});
			alert('비밀번호가 재설정되었습니다.');
			navigate('/login');
		} catch (error) {
			console.log(error);
		}
	};

	const onKeyDown = (e) => {
		if (e.key === 'Enter') {
			handleResetPw();
		}
	}

	return (
		<div className="auth-form-container centered-flex">
			<AuthLogo />
			<div className='auth-form'>
				<div className={`input-form ${focused === 'new_password' ? 'input-focus-form' : ''}`}>
					<LockOutlinedIcon className="icons" />
					<input
						type="password"
						placeholder="새 비밀번호"
						name='new_password'
						value={new_password}
						onChange={onChange}
						onFocus={() => setFocused('new_password')}
						onBlur={() => setFocused(null)}
					/>
				</div>
				<div className={`input-form ${focused === 'new_password_check' ? 'input-focus-form' : ''}`}>
					<LockOutlinedIcon className="icons" />
					<input
						type="password"
						placeholder="새 비밀번호 확인"
						name='new_password_check'
						value={new_password_check}
						onChange={onChange}
						onKeyDown={onKeyDown}
						onFocus={() => setFocused('new_password_check')}
						onBlur={() => setFocused(null)}
					/>
				</div>
				{
					!pwMatch &&
					<div className='error-text'>
						비밀번호가 일치하지 않습니다.
					</div>
				}
				<div className="auth-button" onClick={handleResetPw}>
					비밀번호 재설정
				</div>
			</div>
		</div>
	);
};

export default ResetPwPage;