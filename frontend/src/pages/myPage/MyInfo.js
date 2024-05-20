import React, {useState, useEffect} from "react";
import styles from '../myPage/MyInfo.module.css';
import {defaultInstance} from '../../utils/axiosInstance';

const MyInfo = () => {
    const [myInfo, setMyInfo] = useState({});
    const user_id = 1
    const getMyInfo = () => {
        defaultInstance.get(`/mypage/myInfo?user_id=${user_id}`
        )
            .then((res)=> {
                setMyInfo(res.data)
            })
            .catch(err => {console.log(err)})
    }
    useEffect(() => {
        getMyInfo()
    }, []);

    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    <div className={styles.text}>성명</div>
                    <div className={styles.userText}>{myInfo.name}</div>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.text}>닉네임</div>
                    <div className={styles.userText}>{myInfo.nickname}</div>
                    <button className={styles.nicknameBtn}>수정</button>
                </div>
                <div className={styles.divBox}>
                    <div className={styles.text}>전화번호</div>
                    <div className={styles.userText}>{myInfo.phone}</div>
                </div>
                <div className={styles.btnBox}>
                    <button className={styles.userBtn}>회원탈퇴</button>
                </div>
            </div>
        </>
    )
}

export default MyInfo;