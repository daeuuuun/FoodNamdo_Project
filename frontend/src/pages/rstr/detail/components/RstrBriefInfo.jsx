import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";
import palette from "../../../../styles/palette";
import PlaceIcon from '@mui/icons-material/Place';
import CallIcon from '@mui/icons-material/Call';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import StarIcon from '@mui/icons-material/Star';
import { AppContext } from '../../../../utils/loginContext';
import { authBackInstance } from './../../../../utils/axiosInstance';

const RstrBriefInfoContainer = styled.div`
    display: flex;
    flex-direction: column;
    margin: 0 1rem;
    width: 60%;
    font-size: 1.1rem;
    // background-color: ${palette.lightblue};

    & > div {
        margin-bottom: 0.8rem;
    }
`;

const RstrName = styled.div`
    font-family: 'Gmarket Sans Bold';
    font-size: 2.5rem;
`

const RstrReviewInfo = styled.div`
    & > div {
        margin-bottom: 0.3rem;
        font-size: 1.1rem;
    }

    & > div > span {
        font-family: 'Gmarket Sans Bold';
        font-size: 1.1rem;
        margin-right: 0.3rem;
        color: #03c75a;
    }
`;

const RstrInfos = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    
    .scroll-map {
        background-color: ${palette.lightblue};
        border-radius: 10px;
        width: 100px;
        height: 30px;

        font-size: 0.9rem;
        box-shadow: 2px 1px 2px ${palette.gray};
    }

    .scroll-map:hover {
        cursor: pointer;
        background-color: ${palette.lightblue2};
    }

    div {
        font-size: 0.9rem;
    }
`;

const CertificationMarks = styled.div`
    display: flex;
    color: #ffffff;
    div {
        width: 70px;
        height: 30px;
        font-size: 0.9rem;
        text-align: center;
        line-height: 30px;
        margin-right: 7px;
        border-radius: 10px;
    }

    #example-mark {
        background-color: #008A50;
    }

    #relax-mark {
        background-color: #254871;
    }
`;

const FavoriteButton = styled.div`
    background-color: #ffffff;
    border: 1px solid ${palette.gray};
    border-radius: 50%;
    width: 3.2rem;
    height: 3.2rem;
    box-shadow: 2px 1px 2px ${palette.gray};

    &:hover {
        background-color: ${palette.lightgray};
        cursor: pointer;
    }

    .favorite-count {
        font-size: 0.7rem;
    }
`;

const RstrIconAndInfo = styled.div`
    display: flex;
    align-items: center;

    div {
        font-size: 1.1rem;
    }
`;

const FavoriteButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
`


const StyledStarIcon = styled(StarIcon)`
    color: ${palette.yellow};
    margin-bottom: 1px;
`;

const StyledPlaceIcon = styled(PlaceIcon)`
    color: ${palette.darkblue1};
    margin-bottom: 1px;
`;

const StyledCallIcon = styled(CallIcon)`
    color: ${palette.darkblue1};
    margin-bottom: 1px;
`;

const StyledBookmarkBorderIcon = styled(BookmarkBorderIcon)`
    color: ${palette.darkblue2};
    margin-bottom: -8px;
`;

const StyledBookmarkIcon = styled(BookmarkIcon)`
    color: ${palette.darkblue2};
    margin-bottom: -8px;
`;

const RstrBriefInfo = ({ rstrInfo, rstrId }) => {
    const { isAuthenticated } = useContext(AppContext);
    const navigate = useNavigate();
    const [isFavorite, setIsFavorite] = useState(false);

    const MoveToTop = () => {
        window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' })
    }

    useEffect(() => {
        const checkFavorite = async () => {
            try {
                const response = await authBackInstance.get(`/mainsystem/RstrFavoriteRegister`, {
                    params: {
                        rstr_id: rstrId
                    }
                });
                if (response.status === 200) {
                    setIsFavorite(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        }

        if (isAuthenticated) {
            checkFavorite();
        }
    }, []);

    const toggleFavorite = async () => {
        if (!isAuthenticated) {
            alert("로그인이 필요합니다.");
            navigate('/login');
            return;
        }

        console.log(rstrId);
        try {
            const response = await authBackInstance.post('/mainsystem/RstrFavoriteRegister', {
                rstrId: rstrId
            });
            if (response.status === 200) {
                setIsFavorite(response.data);
            }
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <RstrBriefInfoContainer>
            <CertificationMarks>
                {rstrInfo.example && <div id='example-mark'>모범식당</div>}
                {rstrInfo.relax && <div id='relax-mark'>안심식당</div>}
            </CertificationMarks>
            <RstrName>{rstrInfo.rstr_name}</RstrName>
            <RstrReviewInfo>
                <RstrIconAndInfo style={{ fontSize: '1.1rem' }}>
                    <StyledStarIcon style={{ fontSize: '1.7rem' }} />
                    {`${rstrInfo.rstr_review_rating} (${rstrInfo.rstr_review_count}명)`}
                </RstrIconAndInfo>
                {
                    rstrInfo.rstr_naver_rating &&
                    <div>
                        <span>네이버평점</span>
                        {`${rstrInfo.rstr_naver_rating}`}
                    </div>
                }
            </RstrReviewInfo>
            <RstrInfos>
                <RstrIconAndInfo>
                    <StyledPlaceIcon />
                    <div>{rstrInfo.rstr_address}</div>
                </RstrIconAndInfo>
                <div
                    className='scroll-map centered-flex'
                    onClick={MoveToTop}
                >지도보러가기</div>
            </RstrInfos>
            <RstrIconAndInfo>
                <StyledCallIcon />
                <div>{rstrInfo.rstr_tel}</div>
            </RstrIconAndInfo>
            <FavoriteButtonWrapper>
                <FavoriteButton className='centered-flex' onClick={toggleFavorite}>
                    <div>
                        {isFavorite ?
                            <StyledBookmarkIcon style={{ fontSize: '2.2rem' }} /> :
                            <StyledBookmarkBorderIcon style={{ fontSize: '2.2rem' }} />
                        }
                    </div>
                    <div className='favorite-count'>{rstrInfo.rstr_favorite_count}</div>
                </FavoriteButton>
            </FavoriteButtonWrapper>
        </RstrBriefInfoContainer>
    )
}

export default RstrBriefInfo;
