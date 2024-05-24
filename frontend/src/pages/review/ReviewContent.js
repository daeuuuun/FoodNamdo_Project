import React, {useState} from "react";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";

const ReviewContent = () => {
    const [text,setText] = useState('')
    const maxLength = 300;

    const handleClick = (event) => {
        const {value} = event.target
        if (value.length <= maxLength) {
            setText(value)
        }
    }

    return (
        <>
            <div className={styles.contentDiv}>
                <textarea value={text} onChange={handleClick} placeholder={"리뷰를 입력해주세요."} rows="50" cols="80" className={styles.content}/>
            </div>
        </>
    )
};

export default ReviewContent;