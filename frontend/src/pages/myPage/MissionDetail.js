import React from 'react';
import styles from "./Mission.module.css";
import {DiRuby} from "react-icons/di";

const MissionDetail = ({name}) => {
        return (
            <>
                <div className={styles.detailDiv}>
                    <div className={styles.badge}>
                        <DiRuby/>
                    </div>
                    <div className={styles.contentBox}>
                        <div className={styles.textDiv}>
                            <div className={styles.text}>{name}</div>
                        </div>
                    </div>
                </div>
            </>
        )
    }
;

export default MissionDetail;