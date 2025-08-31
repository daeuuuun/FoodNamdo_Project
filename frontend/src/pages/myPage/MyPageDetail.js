import React from "react";
import styles from '../myPage/Sidebar.module.css';

const MyPageDetail = ({ id, title, handleClick, active }) => {
    return (
        <>
            <div className={`${styles.sideBar} ${active ? styles.active : ''}`} id={id} onClick={() => handleClick(id)}>
                <div className={styles.sideBarText}>
                    {title}
                </div>
            </div>
        </>
    )
}

export default React.memo(MyPageDetail);