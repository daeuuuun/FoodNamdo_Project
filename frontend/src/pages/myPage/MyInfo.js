import React from "react";
import styles from '../myPage/MyInfo.module.css';

const MyInfo = () => {
    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    <div className={styles.text}>성명</div>
                    <div className={styles.userText}>도레미</div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.text}>닉네임</div>
                    <div className={styles.userText}>냥냥냥</div>
                    <button className={styles.nicknameBtn}>수정</button>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.text}>전화번호</div>
                    <div className={styles.userText}>010-1234-1234</div>
                </div>
                <div className={styles.btnBox}>
                    <button className={styles.userBtn}>회원탈퇴</button>
                </div>
            </div>
        </>
    )
}

export default MyInfo;