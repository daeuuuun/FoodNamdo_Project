import React from "react";
import styles from '../myPage/MyPage.module.css';
import SideBar from "./SideBar";

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