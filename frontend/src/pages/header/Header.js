import React, { useContext } from 'react';
import styles from '../header/Header.module.css';
import FoodNamdoLogo from '../header/FoodNamdoLogo.png';
import SearchBar from '../../components/common/SearchBar';
import { Link, useNavigate } from 'react-router-dom';
import { defaultBackInstance } from '../../utils/axiosInstance';
import { BACKEND_SERVER_URL } from '../../config/Config';
import { AppContext } from '../../utils/loginContext';

const Header = () => {
    const navigate = useNavigate();
    const { isAuthenticated, logout } = useContext(AppContext);
    const refreshToken = localStorage.getItem('refreshToken');

    const handleLogout = async () => {
        try {
            await defaultBackInstance.post(BACKEND_SERVER_URL + '/usermanagement/logout', {
                refreshToken: refreshToken
            });
            navigate('/');
            logout();
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <>
            <div className={styles.headerContainer}>
                <div className={styles.containTop}>
                    <Link to="/"><img src={FoodNamdoLogo} alt="로고" className={styles.logoImage} /></Link>
                    <SearchBar />
                    {
                        isAuthenticated ? (
                            <div className={styles.sign}>
                                <div className={styles.signIn} onClick={handleLogout}>로그아웃</div>
                            </div>
                        ) : (
                            <div className={styles.sign}>
                                <div className={styles.signIn}><Link to="/login">로그인</Link></div>
                                <div className={styles.signUp}><Link to="/signup">회원가입</Link></div>
                            </div>
                        )
                    }
                </div>
            </div>
        </>
    )
}

export default Header;