import React, { useContext, useEffect } from "react";
import styles from '../myPage/MyPage.module.css';
import SideBar from "./SideBar";
import Header from "../header/Header";
import { AppContext } from "../../utils/loginContext";
import { useNavigate } from "react-router-dom";

const MyPage = () => {
    const { isAuthenticated } = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            alert("로그인이 필요합니다.");
            goLogin();
        }
    }, []);

    const goLogin = () => {
        navigate("/login");
    }

    return (
        <>
            <div className={styles.background}>
                <Header />
                <SideBar />
            </div>
        </>
    )
}

export default MyPage;
