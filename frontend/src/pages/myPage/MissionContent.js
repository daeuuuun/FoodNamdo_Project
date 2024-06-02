import React, {useEffect, useMemo, useState} from "react";
import styles from '../myPage/Mission.module.css';

import {authBackInstance} from "../../utils/axiosInstance";
import MissionDetail from "./MissionDetail";

const MissionContent = () => {
    const [mission, setMission] = useState({
        badge: [],
        userBadge: []
    });

    useEffect(() => {
        const getMission = async () => {
            try {
                const res = await authBackInstance.get(`/mypage/myBadge`);
                setMission(res.data);
                console.log(res);
            } catch (err) {
                console.log(err);
            }
        };
        getMission();
    }, []);

    const myBadges = useMemo(() => {
        const myBadgeIds = mission.userBadge.map(badge => badge.badge_id);
        const myBadgeNames = mission.badge
            .filter(badge => myBadgeIds.includes(badge.badge_id))
            .map(badge => badge.badge_name);

        return myBadgeNames;
    }, [mission]);

    return (
        <>
            <div className={styles.background}>
                <div className={styles.divBox}>
                    {myBadges.map((badge, index) => (
                        <MissionDetail key={index} name={badge} />
                    ))}
                </div>
            </div>
        </>
    );
};

export default MissionContent;
