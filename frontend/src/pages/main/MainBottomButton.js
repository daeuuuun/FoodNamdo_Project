import React from "react";
import styles from '../main/MainBottomButton.module.css';

const MainBottomButton = () => {
    return (
        <>
            <div className={styles.bottomTextDiv}>
                <button className={styles.btn}>음식점 전체보기</button>
                <button className={styles.btn}>영수증 인증</button>
                <button className={styles.btn}>마이페이지</button>
            </div>
        </>
    )
}

export default React.memo(MainBottomButton);