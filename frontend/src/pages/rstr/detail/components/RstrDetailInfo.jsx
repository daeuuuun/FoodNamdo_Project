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
    margin: 2.5rem 0;
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
                    {rstrInfo.rstr_closed != 'null' ? <div>{rstrInfo.rstr_closed}</div> : <div>휴무일 정보 없음</div>}
                </InfoContent>
            </InfoContainer>
            <InfoContainer>
                <InfoTitle>메뉴정보</InfoTitle>
                <InfoContent>
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
                    </MenuTable>
                </InfoContent>
            </InfoContainer>
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
        </RstrDetailInfoContainer>
    )
}

export default RstrDetailInfo;