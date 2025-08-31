import React, {useEffect, useState} from "react";
import styles from "./ReviewContain.module.css";
import {IoMdCheckmarkCircleOutline} from "react-icons/io";
import {PiClover} from "react-icons/pi";
import {authBackInstance} from "../../../../utils/axiosInstance";
import Review from "./Review";
import {useParams} from "react-router-dom";

const ReviewContain = ({ rstrInfo }) => {
    const [userInfo, setUserInfo] = useState({});
    const {rstrId} = useParams();
    const getUserInfo = () => {
        authBackInstance.get(`mainsystem/RstrDetail?rstr_id=${rstrId}`
        )
            .then((res) => {
                setUserInfo(res.data)
            })
            .catch(err => {console.log(err)})
    }

    useEffect(() => {
        getUserInfo()
    }, []);

    return (
        <>
            <div className={styles.reviewContainer}>
                {rstrInfo.reviews.map(review => {
                    return <Review key = {review.review_id} item = {review}/>
                })}
            </div>
        </>
    )
};

export default ReviewContain;