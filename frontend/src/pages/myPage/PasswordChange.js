import React, {useRef} from "react";
import styles from '../myPage/PasswordChange.module.css';
import {defaultBackInstance} from "../../utils/axiosInstance";

const PasswordChange = () => {
    const currentPwRef = useRef(null)
    const nextPwRef = useRef(null)
    const reTryPwRef = useRef(null)
    const changePw = () => {
        if (nextPwRef.current.value === reTryPwRef.current.value) {
            defaultBackInstance.post(`/mypage/changePassword?user_id=${1}&password=${nextPwRef.current.value}`)
                .then(resp => console.log(resp))
                .catch(err => console.log(err))
        }
    }
    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    <div className={styles.pw}>비밀번호</div>
                    <div className={styles.pwTextDiv}>
                        <input ref={currentPwRef} className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.pw}>새 비밀번호</div>
                    <div className={styles.pwTextDiv}>
                        <input ref={nextPwRef} className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.pw}>재입력</div>
                    <div className={styles.pwTextDiv}>
                        <input ref={reTryPwRef} className={styles.pwText} type={"password"}></input>
                    </div>
                </div>
                <div className={styles.btnBox}>
                    <button onClick={changePw} className={styles.changeBtn}>변경하기</button>
                </div>
            </div>
        </>
    )
}

export default PasswordChange;