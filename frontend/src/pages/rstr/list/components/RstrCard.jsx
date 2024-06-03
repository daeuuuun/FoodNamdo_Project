import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import StarRoundedIcon from '@mui/icons-material/StarRounded';
import { useNavigate } from 'react-router-dom';
import BookmarkIcon from '@mui/icons-material/Bookmark';

const RstrCardContainer = styled.div`
    margin: 5px 9px;
    padding: 0.5rem;
    width: 195px;
    border-radius: 10px;
    border: 1px solid ${palette.lightblue};
    box-shadow: 2px 1px 2px ${palette.gray};
    background-color: ${palette.lightblue};
    
    &:hover {
        cursor: pointer;
        box-shadow: 4px 2px 4px ${palette.gray};
    }
`
const ImgContainer = styled.div`
    position: relative;
    width: 100%;
    height: 10rem;
    overflow: hidden;
    border-radius: 10px;

    .rstr-favorite {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 65px;
        position: absolute;
        top: 0;
        right: 0;
        background-color: rgba(255, 255, 255, 0.8);
        padding: 2px;
        border-radius: 0 0 0 10px;
        font-size: 0.9rem;
    }
`;

const StyledFavoriteIcon = styled(BookmarkIcon)`
    color: ${palette.blue};
`;

const Img = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
`;

const RstrInfo = styled.div`
    display: flex;
    align-items: center;
    &.title {    
        padding: 8px 0 0 0;
        font-family: 'Gmarket Sans Bold';
        justify-content: center;
        font-size: 1.1rem;
    }

    &.info {
        justify-content: space-between;
        display: flex;
        font-size: 0.9rem;
    }

    &.info .rating {
        flex-direction: row;
        font-size: 0.9rem;
    }
`;

const StyledStarIcon = styled(StarRoundedIcon)`
    color: ${palette.yellow};
    margin-right: -2px;
`;

const CertificationMarks = styled.div`
    display: flex;
    justify-content: center;
    color: ${palette.lightblue};
    height: 30px;
    div {
        width: 60px;
        height: 25px;
        font-size: 0.7rem;
        text-align: center;
        line-height: 1.8rem;
        margin-right: 2px;
        margin-bottom: 3px;
        border-radius: 10px;
    }

    #example-mark {
        background-color: #008A50;
    }

    #relax-mark {
        background-color: #254871;
    }
`;

const RstrCard = ({ rstrInfo, mode }) => {

    const navigate = useNavigate();

    return (
        <RstrCardContainer
            style={{
                backgroundColor: mode == 'rstrList' ? palette.lightblue : '#ffffff'
            }}
            onClick={() => {
                navigate(`/rstr/${rstrInfo.rstr_id}`, { state: { rstrInfo } })
            }}
        >
            <CertificationMarks>
                {rstrInfo.example && <div id='example-mark'>모범식당</div>}
                {rstrInfo.relax && <div id='relax-mark'>안심식당</div>}
            </CertificationMarks>
            <ImgContainer>
                {rstrInfo.rstr_img_url ? (
                    <Img src={rstrInfo.rstr_img_url} alt='이미지' />
                ) : (
                    <Img src="/img/noimage.png" alt='이미지 없음' />
                )}
                {
                    mode == 'rstrList' && (
                        <div className='rstr-favorite'>
                            <StyledFavoriteIcon fontSize="small" />
                            <div style={{ width: '10px', marginTop: '2px' }}>
                                {rstrInfo.rstr_favorite_count}
                            </div>
                        </div>
                    )
                }
            </ImgContainer>
            <RstrInfo className='title'>
                {rstrInfo.rstr_name.length > 8 ? rstrInfo.rstr_name.slice(0, 10) + '...' : rstrInfo.rstr_name}
            </RstrInfo>
            <RstrInfo className='info'>
                <div style={{ marginTop: '1px' }}>
                    <span>{rstrInfo.rstr_region}</span>
                    <span> | </span>
                    <span>
                        {rstrInfo.category_name.length > 3 ? rstrInfo.category_name.slice(0, 3) + '...' : rstrInfo.category_name}
                    </span>
                </div>
                <div className='centered-flex rating'>
                    <StyledStarIcon />
                    <div style={{ marginTop: '1px' }}>
                        {`${rstrInfo.rstr_review_rating} (${rstrInfo.rstr_review_count}명)`}
                    </div>
                </div>
            </RstrInfo>
        </RstrCardContainer>
    )
}

export default RstrCard;