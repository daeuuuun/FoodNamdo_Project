import React, { useState, useEffect } from 'react';
import styled from "styled-components";
import RstrList from './components/RstrList';
import palette from '../../../styles/palette';

const RstrListPageContainer = styled.div`
    display: flex;
`;

const CategoryBarContainer = styled.div`
    width: 20%;
`

const CategoryBar = styled.div`
    width: 80%;
    padding: 30px 15px;
    height: 250px;
    background-color: ${palette.lightgray};
    border: 1px solid ${palette.lightblue};
    border-radius: 10px;
`

const CategoryTitle = styled.div`
    text-align: center;
    font-size: 1.1rem;
    padding-bottom: 10px;
`

export const RstrListPage = () => {

    // 여기서 useState 관리

    return (
        <RstrListPageContainer>
            <CategoryBarContainer>
                <CategoryBar>
                    <CategoryTitle>카테고리</CategoryTitle>
                    <div></div>
                </CategoryBar>
            </CategoryBarContainer>
            <RstrList></RstrList>
        </RstrListPageContainer>
    )
}

export default RstrListPage;