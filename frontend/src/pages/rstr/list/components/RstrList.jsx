import React, { useState } from 'react';
import styled from 'styled-components';
import palette from '../../../../styles/palette';
import RstrCard from "./RstrCard";

import { Link, useLocation } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';
import PaginationItem from '@mui/material/PaginationItem';

const RstrListContainer = styled.div`
    padding: 0 5px;
    width: 100%;
    border: 1px solid gray;
    display: flex;
    flex-direction: column;
    .total-rstr-num {
        font-size: 0.75rem;
        // color: ${palette.darygray};
        margin: 10px;
        text-align: right;
    }
`

const RstrCards = styled.div`
    display: flex;
    flex-wrap: wrap;
`

// const Pagination = styled.div`
//     display: flex;
//     justify-content: center;
//     margin-top: 20px;
// `;

const PageButton = styled.button`
    margin: 0 5px;
    padding: 5px 10px;
    border: none;
    background-color: ${({ active }) => active ? '#333' : '#ccc'};
    color: ${({ active }) => active ? '#fff' : '#000'};
    cursor: pointer;
`;

const RstrList = () => {

    const [rstrList, setrstrList] = useState([]);
    // const [currentPage, setCurrentPage] = useState(1);
    // const pageSize = 8;
    // const pageCount = Math.ceil(rstrList.length / pageSize);
    // const currentPageData = rstrList.slice((currentPage - 1) * pageSize, currentPage * pageSize);

    // const handlePageChange = (page) => {
    //     setCurrentPage(page);
    // }

    const location = useLocation();
    const query = new URLSearchParams(location.search);
    const page = parseInt(query.get('page') || '1', 10);

    return (
        <RstrListContainer>
            <div className='total-rstr-num'>{`약 ${210}건`}</div>
            <RstrCards>
                <RstrCard />
                <RstrCard />
                <RstrCard />
                <RstrCard />
                <RstrCard />
            </RstrCards>
            <div style={{ margin: '20px auto' }}>
                <Pagination
                    page={page}
                    count={10}
                    color="primary"
                    renderItem={(item) => (
                        <PaginationItem
                            component={Link}
                            to={`/${item.page === 1 ? '' : `page=${item.page}`}`}
                            {...item}
                        />
                    )}
                />
            </div>
        </RstrListContainer>
    )
}

export default RstrList;