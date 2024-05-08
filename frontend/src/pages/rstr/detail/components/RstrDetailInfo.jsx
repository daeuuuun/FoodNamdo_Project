import React from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import AdditionInfo from './AdditionInfo';
import RstrMap from './RstrMap';
import { useLocation } from 'react-router-dom';

const RstrDetailInfoContainer = styled.div`
    padding: 10px;
`;

const InfoContainer = styled.div`
    padding: 40px 0;
    border-bottom: 1px dotted ${palette.gray};
`;

const InfoTitle = styled.div`
    font-family: 'Gmarket Sans Medium';
    font-size: 1.5rem;
    color: ${palette.darkblue2}
`;

const InfoContent = styled.div`
    margin-top: 0.8rem;
    font-size: 1rem;
    line-height: 25px;
`;

const MenuTable = styled.div`
    .menu-table {
        width: 40%;
        border-collapse: collapse;
    }
    th, td {
        border: 1px solid black;
        padding: 3px;
        text-align: left;
        font-size: 0.9rem;
    }
    th {
        background-color: #f2f2f2;
    }
    .menu-table-container {
        margin: 20px;
    }
`;

//

const AdditionInfoContainer = styled.div`
    display: flex;
    justify-content: flex-start;
`;

const AdditionInfoContent = styled.div`
    width: 5.2rem;
    height: 5.2rem;
    margin-right: 20px;
    border: 1px solid ${palette.darkblue1};
    border-radius: 50%;
    text-align: center;
     box-shadow: 2px 1px 2px ${palette.gray};
`;
const AdditionInfoLabel = styled.div`
    font-family: 'Gmarket Sans Medium';
    margin: 0 0.6rem 0 0.6rem;
    font-size: 0.8rem;
`;
const RstrDetailInfo = () => {

    const location = useLocation();
    const { rstrInfo } = location.state;

    return (
        <RstrDetailInfoContainer>
            <InfoContainer>
                <InfoTitle>소개내용</InfoTitle>
                <InfoContent>{rstrInfo.rstr_intro}</InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>운영시간</InfoTitle>
                <InfoContent>
                    {rstrInfo.rstr_business_hour != 'null' ? <div>{rstrInfo.rstr_business_hour}</div> : <div>운영시간 정보 없음</div>}
                    {rstrInfo.rstr_closed != 'null' ? <div>휴무일 - {rstrInfo.rstr_closed}</div> : <div>휴무일 정보 없음</div>}
                </InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>메뉴정보</InfoTitle>
                <InfoContent>
                    {rstrInfo.menu_description[0]?.menu_description_id ? (
                        <MenuTable className='menu-table-container'>
                            <table className='menu-table'>
                                <tr>
                                    <th>분류</th>
                                    <th>메뉴명</th>
                                    <th>가격</th>
                                </tr>
                                {rstrInfo.menu_description.map((menu) => (
                                    <tr key={menu.menu_description_id}>
                                        <td>{menu.menu_category_sub != 'null' ? menu.menu_category_sub : ''}</td>
                                        <td>{menu.menu_name}</td>
                                        <td>{menu.menu_price}</td>
                                    </tr>
                                ))}
                            </table>
                        </MenuTable >
                    ) : (<>메뉴 정보 없음</>)
                    }
                </InfoContent >
            </InfoContainer >
            <InfoContainer>
                <InfoTitle>추가정보</InfoTitle>
                <InfoContent>
                    <AdditionInfo rstrInfo={rstrInfo} />
                </InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>음식점 위치</InfoTitle>
                <InfoContent>
                    <div>{rstrInfo.rstr_address}</div>
                    <RstrMap rstrInfo={rstrInfo} />
                </InfoContent>
            </InfoContainer>
        </RstrDetailInfoContainer >
    )
}

export default RstrDetailInfo;