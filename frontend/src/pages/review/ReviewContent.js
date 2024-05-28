import React, {useEffect, useState} from "react";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";

const ReviewContent = ({ onContentChange }) => {
    const [text, setText] = useState("");
    const maxLength = 300;

    const handleChange = (event) => {
        const { value } = event.target;
        if (value.length <= maxLength) {
            setText(value);
            onContentChange(value);
        }
    };

    return (
        <div className={styles.contentDiv}>
            <textarea
                value={text}
                onChange={handleChange}
                placeholder="리뷰를 입력해주세요."
                rows="50"
                cols="80"
                className={styles.content}
            />
        </div>
    );
};

export default ReviewContent;
