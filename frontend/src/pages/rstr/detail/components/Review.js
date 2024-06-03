import React, { useState } from "react";
import ReviewCheck from "./ReviewCheck";
import styles from "./ReviewContain.module.css";
import { IoHeartOutline } from "react-icons/io5";
import { MdOutlineHeartBroken } from "react-icons/md";
import { IoHeart } from "react-icons/io5";
import { MdHeartBroken } from "react-icons/md";
import { authBackInstance } from "../../../../utils/axiosInstance";
import { IoMdCheckmarkCircleOutline } from "react-icons/io";
import { PiClover } from "react-icons/pi";

const Review = ({ item }) => {
    console.log(item)

    const [toggleLike, setToggleLike] = useState(false);
    const [toggleDislike, setToggleDislike] = useState(false);

    const handleLike = () => {
        if (!toggleLike) {
            authBackInstance.post('/mainsystem/reactionReview', {
                reviewId: item.review_id,
                reactionType: "LIKE",
            }).then(res => { setToggleLike(true) }).catch(err => console.log(err))
        }
    }

    const handleDislike = () => {
        if (!toggleDislike) {
            authBackInstance.post('/mainsystem/reactionReview', {
                reviewId: item.review_id,
                reactionType: "DISLIKE",
            }).then(res => { setToggleDislike(true) }).catch(err => console.log(err))
        }
    }

    return (
        <>
            <div className={styles.backgroundDiv}>
                <div className={styles.infoDiv}>
                    <div className={styles.nicknameText}>{item.userNickName}</div>
                    <div className={styles.badge}>
                        <IoMdCheckmarkCircleOutline className={styles.icons} />
                        <PiClover className={styles.icons} />
                    </div>
                    <div className={styles.nullDiv}></div>
                    <div className={styles.heartReview}>
                        {toggleLike ? <IoHeart className={styles.icons} /> :
                            <IoHeartOutline className={styles.icons} onClick={() => handleLike()} />}
                        {toggleLike ? item.like + 1 : item.like}
                        {toggleDislike ? <MdOutlineHeartBroken className={styles.icons} /> :
                            <MdHeartBroken className={styles.icons} onClick={() => handleDislike()} />}
                        {toggleDislike ? item.dislike + 1 : item.dislike}
                    </div>
                </div>
                <ReviewCheck rstrInfo={item} />
                <div className={styles.reviewTextDiv}>
                    <div className={styles.textDiv}>
                        {item.review_text}
                    </div>
                </div>
                <div className={styles.imagesDiv}>
                    {item.reviewImgs && item.reviewImgs.map((img, index) => (
                        <img key={index} src={img.review_img_url} alt={`Review Image ${index}`} className={styles.reviewImage} />
                    ))}
                </div>
                <div className={styles.reviewTime}>{item.time_of_creation}</div>
            </div>
        </>
    )
};

export default Review;
