import React, { useState, useEffect, useRef, useContext } from "react";
import { useNavigate } from 'react-router-dom';
import styles from '../myPage/MyInfo.module.css';
import { authBackInstance } from '../../utils/axiosInstance';
import { AppContext } from "../../utils/loginContext";

const MyInfo = () => {

    const { logout } = useContext(AppContext);
    const navigate = useNavigate();

    const [myInfo, setMyInfo] = useState({});

    const nickRef = useRef(null)
    const getMyInfo = () => {
        authBackInstance.get(`/mypage/myInfo`)
            .then((res) => {
                setMyInfo(res.data)
                nickRef.current.value = res.data.nickname
            })
            .catch(err => { console.log(err) })
    }
    const changeNick = () => {
        authBackInstance.post(`/mypage/changeNickname`, {
            nickname: nickRef.current.value
        })
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    const deleteUser = () => {
        authBackInstance.post('/usermanagement/deleteUser')
            .then(res => {
                alert('회원 탈퇴가 완료되었습니다.');
                logout();
                navigate('/')
            })
            .catch(err => console.log(err))
    }

    const handleDeleteUser = () => {

        const confirmed = window.confirm("정말로 회원 탈퇴하시겠습니까?");

        if (confirmed) {
            deleteUser();
        }
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
                <div className={styles.btnBox} onClick={handleDeleteUser}>
                    <button className={styles.userBtn}>회원탈퇴</button>
                </div>
            </div>
        </>
    )
}

export default MyInfo;