import React, {useMemo} from "react";
import styles from "./ReviewCategory.module.css";
import ReviewCategoryDetail from "./ReviewCategoryDetail";

const ReviewCategory = () => {
    const categoryTitle = useMemo(() => [
        { id : 1, title : '맛' },
        { id : 2, title : '가격' },
        { id : 3, title : '청결' },
        { id : 4, title : '친절' },
        { id : 5, title : '편의시설' }
    ], []);

    return (
        <>
            <div className={styles.categoryDiv}>
                {categoryTitle.map(item => {
                    return <ReviewCategoryDetail key={item.id} id={item.id} title={item.title}/>
                })}
            </div>
        </>
    )
};

export default ReviewCategory;