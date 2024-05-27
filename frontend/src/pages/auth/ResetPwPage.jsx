import React, { useState } from 'react';
import AuthLogo from './components/AuthLogo';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';

const ResetPwPage = () => {

	const [focused, setFocused] = useState(null);

	const [resetForm, setResetForm] = useState({
		new_password: '',
		new_password_check: ''
	})

	const { new_password, new_password_check } = resetForm;

	const onChange = (e) => {
		const userResetForm = {
			...resetForm,
			[e.target.name]: e.target.value
		};
		setResetForm(userResetForm);
	}

	const handleResetPw = () => {

	};

	const onKeyDown = (e) => {
		if (e.key === 'Enter') {
			handleResetPw();
		}
	}

	return (
		<div className="auth-form-container centered-flex">
			{/* <AuthLogo /> */}
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
				<div className="auth-button" onClick={handleResetPw}>
					비밀번호 재설정
				</div>
			</div>
		</div>
	);
};

export default ResetPwPage;