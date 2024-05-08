import React, {useState, useCallback, useMemo} from "react";
import styles from '../myPage/Sidebar.module.css';
import MyInfo from "./MyInfo";
import PasswordChange from "./PasswordChange";
import MyReview from "./MyReview";
import MyPageDetail from "./MyPageDetail";

const Sidebar = () => {
    const [currentTabs, setCurrentTabs] = useState(1);
    const sideBars = useMemo(() => [
        { id: 1, element: <MyInfo /> },
        { id: 2, element: <PasswordChange /> },
        { id: 3, element: <MyInfo /> },
        { id: 4, element: <MyReview /> },
        { id: 5, element: <MyInfo /> }
    ], []);

    const sideBarDiv = useMemo(() => [
        { id: 1, title: '내 정보' },
        { id: 2, title: '비밀번호 변경' },
        { id: 3, title: '찜한 가게' },
        { id: 4, title: '내가 남긴 리뷰' },
        { id: 5, title: '도전과제' }
    ], []);

    const handleClick = useCallback((id) => {
        setCurrentTabs(id);
    }, []);

    return (
        <>
            <div className={styles.background}>
                <div className={styles.sideBarContainer}>
                    {sideBarDiv.map(item => {
                        return <MyPageDetail key={item.id} id={item.id} title={item.title} active={currentTabs === item.id} handleClick={handleClick} />
                    })}
                </div>

                <div className={styles.rightContainer}>
                    {sideBars.find(tab => tab.id === currentTabs)?.element}
                </div>
            </div>
        </>
    )
}

export default Sidebar;