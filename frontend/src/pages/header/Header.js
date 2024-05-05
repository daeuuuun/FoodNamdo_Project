import React from 'react';
import styles from '../header/Header.module.css';
import FoodNamdoLogo from '../header/FoodNamdoLogo.png';
import SearchBar from '../../components/common/SearchBar';

const Header = () => {
    return (
        <>
            <div className={styles.headerContainer}>
                <div className={styles.containTop}>
                    <img src={FoodNamdoLogo} alt="로고" className={styles.logoImage}></img>
                    {/* <div className={styles.searchBerContain}>
              <div className={styles.searchBar}></div>
              <CiSearch className={styles.search} />
            </div> */}
                    <SearchBar />
                    <div className={styles.sign}>
                        <div className={styles.signIn}>로그인</div>
                        <div className={styles.signUp}>회원가입</div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Header;