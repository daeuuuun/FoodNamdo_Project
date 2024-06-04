import React, {useEffect, useRef, useState} from 'react';
import styles from '../myPage/MyReview.module.css';
import {authBackInstance} from "../../utils/axiosInstance";
import Review from "../rstr/detail/components/Review";
import {Link} from "react-router-dom";

const ReviewContain = () => {

    const [myReviews, setMyReviews] = useState([]);

    useEffect(() => {
        authBackInstance.get("/mypage/getReview")
            .then(res => {
                setMyReviews(res.data);
                console.log(res.data);
            })
            .catch(err => console.log(err));
    }, []);

    const removeFoodReview = review_id => {
        authBackInstance.post(`/mainsystem/deleteReview?review_id=${review_id}`)
            .then(res => alert("삭제가 완료되었습니다.")).then(res => window.location.reload())
            .catch(err => console.log(err));
    }

    const fileInputRef = useRef(null);

    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            try {
                const response = await authBackInstance.post(`/mainsystem/verifyReview?review_id=${fileInputRef.current.name}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log('Image uploaded successfully:', response);
            } catch (error) {
                console.error('Image upload error:', error);
            }
        }
    };

    const click = () => {
        fileInputRef.current.click()
    }

    return (
        <div className={styles.Contain}>
            {myReviews.map(review => {
                return (
                    <div className={styles.topDiv} key={review.review_id}>
                        <div className={styles.btnDiv}>
                            <div className={styles.storeName}>{review.rstrName}</div>
                            <div className={styles.btns} onClick={click}>영수증</div>
                            <input
                                name={review.review_id}
                                type="file"
                                ref={fileInputRef}
                                style={{display: 'none'}}
                                onChange={handleFileChange}
                            />
                            <div className={styles.btns}>
                                <Link to={`/review/update?review_id=${review.review_id}`}>수정</Link>
                            </div>
                            <div className={styles.btns} onClick={() => removeFoodReview(review.review_id)}>삭제</div>
                        </div>
                        <Review item={review}/>
                    </div>
                )
            })}
        </div>
    );
};

export default ReviewContain;
