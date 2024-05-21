import React, {useState, useEffect, useRef} from "react";
import styles from '../myPage/MyInfo.module.css';
import {defaultInstance} from '../../utils/axiosInstance';

const MyInfo = () => {
    const [myInfo, setMyInfo] = useState({});
    const user_id = 1
    const nickRef = useRef(null)
    const getMyInfo = () => {
        defaultInstance.get(`/mypage/myInfo?user_id=${user_id}`
        )
            .then((res)=> {
                setMyInfo(res.data)
                nickRef.current.value = res.data.nickname
            })
            .catch(err => {console.log(err)})
    }
    const changeNick = () => {
        defaultInstance.post(`/mypage/changeNickname?user_id=${user_id}&nickname=${nickRef.current.value}`
        )
            .then(res => console.log(res))
            .catch(err => console.log(err))
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
                    <div className={styles.sizeDiv}>
                    <input className={styles.nicknameInput} ref={nickRef}></input>
                    </div>
                    <button className={styles.nicknameBtn} onClick={changeNick}>수정</button>
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