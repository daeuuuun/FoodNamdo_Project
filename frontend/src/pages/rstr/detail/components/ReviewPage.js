import React, {useState} from "react";
import styles from './ReviewPage.module.css'
import ReviewContainer from "./ReviewContainer";
import {Link, useParams} from "react-router-dom";

const ReviewPage = ({ rstrInfo }) => {
    const {rstrId} = useParams()

    const [selectOption, setSelectOption] = useState()
    const selectOptionHandle = (event) => {
        const { name, checked } = event.target
        setSelectOption(event.target.value)
    }

    return (
        <>
            <div className={styles.reviewPageContainer}>
                <div className={styles.insertBtnDiv}>
                    <Link to={`/review/insert/${rstrId}`} className={styles.insertBtn}>리뷰 작성</Link>
                </div>
                <div className={styles.radioBoxDiv}>
                    <div className={styles.rableBtn}>
                        <input type="radio" name="option" value="optionAll" checked={selectOption === 'optionAll'} onChange={selectOptionHandle} className={styles.radioBox}/>
                        모두 보기
                    </div>
                    <div className={styles.rableBtn}>
                        <input type="radio" name="option" value="optionReceipt" checked={selectOption === 'optionReceipt'} onChange={selectOptionHandle} className={styles.radioBox}/>
                        영수증 인증
                    </div>
                </div>
                <ReviewContainer rstrInfo={rstrInfo}/>
            </div>
        </>
    )
};

export default ReviewPage;