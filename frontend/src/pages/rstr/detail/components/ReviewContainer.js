import React, {useEffect, useState} from "react";
import styles from "./ReviewContain.module.css";
import {IoMdCheckmarkCircleOutline} from "react-icons/io";
import {PiClover} from "react-icons/pi";
import {authBackInstance} from "../../../../utils/axiosInstance";
import Review from "./Review";

const ReviewContain = ({ rstrInfo }) => {
    const [userInfo, setUserInfo] = useState({});
    const rstr_id = 33445
    const getUserInfo = () => {
        authBackInstance.get(`mainsystem/RstrDetail?rstr_id=${rstr_id}`
        )
            .then((res) => {
                setUserInfo(res.data)
                console.log(res.data)
            })
            .catch(err => {console.log(err)})
    }

    useEffect(() => {
        getUserInfo()
    }, []);

    return (
        <>
            <div className={styles.reviewContainer}>
                <div className={styles.infoDiv}>
                    <div className={styles.nicknameText}>{userInfo.rstr_name}</div>
                    <div className={styles.badge}>
                        <IoMdCheckmarkCircleOutline className={styles.icons}/>
                        <PiClover className={styles.icons}/>
                    </div>
                    <div className={styles.nullDiv}></div>
                    <div className={styles.reviewCount}>[작성한 리뷰 {rstrInfo.reviews.length}개]</div>

                </div>

                {rstrInfo.reviews.map(review => {
                    return <Review key = {review.review_id} item = {review}/>
                })}
            </div>
        </>
    )
};

export default ReviewContain;