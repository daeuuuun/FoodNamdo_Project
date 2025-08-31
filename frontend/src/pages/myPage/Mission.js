import React from "react";
import styles from '../myPage/Mission.module.css';
import MissionContent from "./MissionContent";

const Mission = () => {
    return (
        <>
            <div className={styles.background}>
                <MissionContent />
            </div>
        </>
    )
}

export default Mission;