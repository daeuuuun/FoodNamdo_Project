import React, { useRef, useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./ReviewInsert.module.css";
import ReviewCategory from "../rstr/detail/components/ReviewCategory";
import ReviewContent from "./ReviewContent";
import { authBackInstance } from "../../utils/axiosInstance";

const ReviewInsert = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [rating, setRating] = useState([]);
    const [content, setContent] = useState('');
    const [reviewId, setReviewId] = useState(null); // 초기값을 null로 설정

    const handleRatingChange = (ratingVal) => {
        setRating(ratingVal);
    };

    const handleContentChange = (contentVal) => {
        setContent(contentVal);
    };

    const handleSubmit = async () => {
        try {
            const response = await authBackInstance.post("/mainsystem/ReviewRegister", {
                rstr_id: id,
                review_text: content,
                time_of_creation: new Date().toISOString().slice(0, -1),
                category_rating_taste: rating[0]?.rating,
                category_rating_price: rating[1]?.rating,
                category_rating_clean: rating[2]?.rating,
                category_rating_service: rating[3]?.rating,
                category_rating_amenities: rating[4]?.rating
            });
            setReviewId(response.data.review_id); // 리뷰 아이디 설정
        } catch (err) {
            console.log(err);
        }
    };

    const fileInputRef = useRef(null);

    const handleButtonClick = () => {
        fileInputRef.current.click();
    };

    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        if (file && reviewId) { // reviewId가 설정된 후에 파일 업로드를 진행
            const formData = new FormData();
            formData.append('file', file);
            formData.append('review_id', reviewId); // review_id 추가

            try {
                const response = await authBackInstance.post(`/mainsystem/uploadReviewImage?review_id=${reviewId}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log(response);
                navigate('/mypage'); // 이미지 업로드 후 마이페이지로 이동
            } catch (error) {
                console.log(error);
            }
        } else {
            console.log("Review ID is not set or file is not selected.");
        }
    };

    useEffect(() => {
        if (reviewId !== null) {
            handleFileChange({ target: { files: fileInputRef.current.files } });
        }
    }, [reviewId]);

    return (
        <div className={styles.insertDiv}>
            <div className={styles.categoryDiv}>
                <ReviewCategory onRatingChange={handleRatingChange} />
            </div>
            <div className={styles.contentDiv}>
                <ReviewContent onContentChange={handleContentChange} />
            </div>
            <div className={styles.btnDiv}>
                <button className={styles.btn} onClick={handleButtonClick} disabled={reviewId === null}>이미지 첨부</button>
                <input
                    type="file"
                    ref={fileInputRef}
                    style={{display: 'none'}}
                    onChange={handleFileChange}
                />
                <button className={styles.btn} onClick={handleSubmit}>리뷰 등록</button>
            </div>
        </div>
    );
};

export default ReviewInsert;
