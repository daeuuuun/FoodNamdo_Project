import React from "react";
import styles from '../myPage/MyPage.module.css';
import SideBar from "./SideBar";
import Header from "../header/Header";

const MyPage = () => {
    return (
        <>
            <div className={styles.background}>
                <SideBar/>
            </div>
        </>
    )
}

export default MyPage;