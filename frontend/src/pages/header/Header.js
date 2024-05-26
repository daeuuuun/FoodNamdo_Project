import React from 'react';
import styles from '../header/Header.module.css';
import FoodNamdoLogo from '../header/FoodNamdoLogo.png';
import SearchBar from '../../components/common/SearchBar';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <>
            <div className={styles.headerContainer}>
                <div className={styles.containTop}>
                    <Link to="/"><img src={FoodNamdoLogo} alt="로고" className={styles.logoImage} /></Link>
                    <SearchBar />
                    <div className={styles.sign}>
                        <div className={styles.signIn}><Link to="/login">로그인</Link></div>
                        <div className={styles.signUp}><Link to="/signup">회원가입</Link></div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Header;