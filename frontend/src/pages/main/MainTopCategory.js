import React from "react";
import styles from '../main/MainTopCategory.module.css';

const MainTopCategory = () => {
    return (
        <>
            <div className={styles.mainHeader}>
                <div className={styles.namdoText}>경상남도</div>
                <div className={styles.namdoText}>전라남도</div>
            </div>
        </>
    )
}

export default React.memo(MainTopCategory);