import React from "react";
import styles from '../myPage/Mission.module.css';
import { DiRuby } from "react-icons/di";

const MissionContent = () => {
    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    <div className={styles.badge}>
                        <DiRuby/>
                    </div>
                    <div className={styles.contentBox}>
                        <div className={styles.textDiv}>
                            <div className={styles.text}>리뷰 1개 작성하기</div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default MissionContent;