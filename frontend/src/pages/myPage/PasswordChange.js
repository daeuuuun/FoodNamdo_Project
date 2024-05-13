import React from "react";
import styles from '../myPage/PasswordChange.module.css';

const PasswordChange = () => {
    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    <div className={styles.pw}>비밀번호</div>
                    <div className={styles.pwTextDiv}>
                        <input className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.pw}>새 비밀번호</div>
                    <div className={styles.pwTextDiv}>
                        <input className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.pw}>재입력</div>
                    <div className={styles.pwTextDiv}>
                        <input className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.btnBox}>
                    <button className={styles.changeBtn}>변경하기</button>
                </div>
            </div>
        </>
    )
}

export default PasswordChange;