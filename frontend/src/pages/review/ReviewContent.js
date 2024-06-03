import React, { useEffect, useState } from "react";
import styles from "./ReviewInsert.module.css";

const ReviewContent = ({ onContentChange, initialContent }) => {
    const [content, setContent] = useState('');

    useEffect(() => {
        if (initialContent) {
            setContent(initialContent);
        }
    }, [initialContent]);

    const handleChange = (e) => {
        setContent(e.target.value);
        onContentChange(e.target.value);
    };

    return (
        <textarea
            className={styles.textarea}
            value={content}
            onChange={handleChange}
        />
    );
};

export default ReviewContent;
