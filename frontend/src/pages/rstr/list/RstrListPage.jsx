import React, { useState, useEffect } from 'react';
import styled from "styled-components";
import SortIcon from '@mui/icons-material/Sort';

import palette from '../../../styles/palette';
import { Radio, FormControlLabel } from '@mui/material';
import { useFile } from '../../../data/FileContext';

import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';

import RstrCard from './components/RstrCard';
import { Link, useLocation } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';
import PaginationItem from '@mui/material/PaginationItem';

import { defaultBackInstance, defaultImageInstance } from "../../../utils/axiosInstance";

const RstrListPageContainer = styled.div`
    display: flex;
    margin-top: 20px;
`;

const CategoryBarContainer = styled.div`
    width: 160px;
    margin: 0 5px 0 0;
    padding: 10px 10px;
    height: 100%;
    background-color: ${palette.lightgray};
    border: 1px solid ${palette.lightblue};
    border-radius: 10px;
    box-shadow: 2px 1px 2px ${palette.gray};

    .title {
        text-align: center;
        font-size: 1.1rem;
        font-weight: bold;
        padding: 5px;
        border-bottom: 2px solid ${palette.lightblue2};
    }

    .content {
        margin-top: 5px;
        font-size: 0.6rem;
    }
`

const StyledFormControlLabel = styled(FormControlLabel)`
  .MuiFormControlLabel-label {
    font-size: 0.85rem;
    font-family: 'Gmarket Sans Medium';
    margin: -5px;
    color: ${(props) => (props.isChecked ? `${palette.blue}` : 'black')};
    position: relative;
    top: 2px;
  }
`;

const StyledCheckbox = styled(Radio)`
  &.MuiCheckbox-root {
    transform: scale(0.8);
  }
`;

const StyledButton = styled(Button)`
    font-family: 'Gmarket Sans Medium';
`;

// 음식점 리스트
const RstrListContainer = styled.div`
    padding: 0 5px;
    width: 100%;
    display: flex;
    flex-direction: column;

    .rstr-list-top {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin: 10px 0;
    }

    .total-rstr-num {
        font-size: 0.9rem;
        margin: 0px 20px;
    }

    .rstr-sort-btns {
        display: flex;
        align-items: center;
        margin: 0px 20px;
    }
    
    .rstr-sort-btn {
        border: 1px solid ${palette.darygray};
        color: ${palette.darygray};
        border-radius: 5px;
        padding: 6px;
        margin: 0 5px;
        cursor: pointer;
    }

    .rstr-sort-btn:hover {
        border: 1px solid ${palette.blue};
        color: ${palette.blue};
    }

    .rstr-sort-btn-clicked {
        border: 1px solid ${palette.blue};
        color: ${palette.blue};
        border-radius: 5px;
        padding: 6px;
        margin: 0 5px;
        cursor: pointer;
    }

    .rstr-loading {
        margin: 0 auto;
        margin-top: 10%;
        font-size: 1.8rem;
    }
`

const RstrCards = styled.div`
    display: flex;
    flex-wrap: wrap;
`

export const RstrListPage = () => {

    const { file } = useFile(); // 이미지 파일
    const { setFile } = useFile();
    const location = useLocation();
    const [rstrList, setRstrList] = useState([]); // 음식점 리스트
    const [isLoading, setIsLoading] = useState(true);
    const [isNotInfo, setIsNotInfo] = useState(false);

    const [page, setPage] = useState(1);
    const [totalPage, setTotalPage] = useState(0);
    const [pageSize, setPageSize] = useState(0);
    const [totalRstr, setTotalRstr] = useState(0);

    const [checkedRegion, setCheckedRegion] = useState('전체');
    const [checkedCategory, setCheckedCategory] = useState('전체');

    const [sortOrder, setSortOrder] = useState('distance');
    const [reverse, setReverse] = useState(false);

    const searchParams = new URLSearchParams(location.search);
    const search = searchParams.get('search');

    useEffect(() => {
        const query = new URLSearchParams(location.search);
        const newPage = parseInt(query.get('page') || '1', 10);
        setPage(newPage);
    }, [location]);

    const fetchDataWithFile = async () => { // 이미지 검색
        const formData = new FormData();
        formData.append('file', file);
        const params = new URLSearchParams({
            similarity: 1,
            n_results: 30,
            page_number: page,
            sort_order: sortOrder,
            reverse: reverse,
            category: checkedCategory,
            region: checkedRegion,
        }).toString();

        const url = `/image_to_image/?${params}`;

        try {
            const response = await defaultImageInstance.post(url, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            });
            setRstrList(response.data.rstr);
            setTotalPage(response.data.total_pages);
            setPageSize(response.data.page_size);
            setTotalRstr(response.data.total_rstr);

            // random URL을 session에 저장하는 코드
            sessionStorage.setItem('random', response.data.random);
        } catch (error) {
            if (error.response.status === 404) {
                setIsNotInfo(true);
            }
        }
        setIsLoading(false);
    };

    const fetchDataWithRandom = async () => {
        const savedRandom = sessionStorage.getItem('random');

        const params = new URLSearchParams({
            page_number: page,
            sort_order: sortOrder,
            reverse: reverse,
            category: checkedCategory,
            region: checkedRegion,
        }).toString();

        const url = `/${savedRandom}/?${params}`;

        try {
            const response = await defaultImageInstance.get(url);
            setRstrList(response.data.rstr);
            setTotalPage(response.data.total_pages);
            setPageSize(response.data.page_size);
            setTotalRstr(response.data.total_rstr);
        } catch (error) {
            if (error.response.status === 404) {
                setIsNotInfo(true);
            }
        }
        setIsLoading(false);
    };

    const fetchDataWithName = async () => {
        const params = new URLSearchParams({
            rstrName: search,
            page: page,
            category: checkedCategory,
            region: checkedRegion,
            sort: sortOrder
        }).toString();

        const url = `/mainsystem/findRstrByName?${params}`;
        try {
            const response = await defaultBackInstance.get(url);
            setRstrList(response.data.rstr);
            setTotalPage(response.data.total_pages);
            setPageSize(response.data.page_size);
            setTotalRstr(response.data.total_rstr);

            if (response.data.rstr.length === 0) {
                setIsNotInfo(true);
            }

        } catch (error) {
            console.log(error);
        }
        setIsLoading(false);

        sessionStorage.removeItem('random');
    };

    // 검색 시
    useEffect(() => {
        loadData();
    }, [file, page, search, checkedRegion, checkedCategory, sortOrder]);

    const loadData = () => {
        setIsLoading(true);
        setIsNotInfo(false);

        if (file && page === 1) { // 이미지 파일
            fetchDataWithFile();
        } else if (search) { // 검색어
            fetchDataWithName();
        } else {
            setFile(null);
            fetchDataWithRandom();
        }
    };

    const handleBasicSort = () => {
        setSortOrder('distance');
        setReverse(false);
        setPage(1);
    }

    const handleReviewSort = () => {
        setSortOrder('rstr_review_rating');
        setReverse(true);
        setPage(1);
    }

    const handleReviewCountSort = () => {
        setSortOrder('rstr_review_count');
        setReverse(true);
        setPage(1);
    }

    const handleFavoriteSort = () => {
        setSortOrder('rstr_favorite_count');
        setReverse(true);
        setPage(1);
    }

    const regionOptions = [
        '전체', '경상남도', '전라남도'
    ];

    const categoryOptions = [
        '전체', '한식', '중식', '일식', '카페/제과점', '양식', '치킨/호프',
        '분식', '식육(숯불구이)', '회', '패스트푸드', '푸드트럭', '외국음식전문점', '뷔페식', '기타'
    ];

    const handleRegionCheckboxChange = (e) => {
        setCheckedRegion(e.target.checked ? e.target.name : null);
        setPage(1);
    }

    const handleCheckboxChange = (e) => {
        setCheckedCategory(e.target.checked ? e.target.name : null);
        setPage(1);
    }

    return (
        <RstrListPageContainer>
            <CategoryBarContainer>
                <div className='title'>지역</div>
                <div className='content'>
                    {regionOptions.map((region) => (
                        <div key={region} style={{ marginTop: '-8px' }}>
                            <StyledFormControlLabel
                                isChecked={checkedRegion === region}
                                control={
                                    <StyledCheckbox
                                        checked={checkedRegion === region}
                                        onChange={handleRegionCheckboxChange}
                                        name={region}
                                        size="small"
                                    />
                                }
                                label={region}
                            />
                        </div>
                    ))}
                </div>
                <div className='title'>카테고리</div>
                <div className='content'>
                    {categoryOptions.map((category) => (
                        <div key={category} style={{ marginTop: '-8px' }}>
                            <StyledFormControlLabel
                                isChecked={checkedCategory === category}
                                control={
                                    <StyledCheckbox
                                        checked={checkedCategory === category}
                                        onChange={handleCheckboxChange}
                                        name={category}
                                        size="small"
                                    />
                                }
                                label={category}
                            />
                        </div>
                    ))}
                </div>
            </CategoryBarContainer>
            <RstrListContainer>
                {
                    isNotInfo ? (
                        <div className='rstr-loading'>일치하는 결과 값이 없습니다</div>
                    ) : (
                        isLoading ?
                            (<div className='rstr-loading'>로딩중입니다. 잠시만 기다려주세요!</div>) :
                            <>
                                <div className='rstr-list-top'>
                                    <div className='total-rstr-num'>{`약 ${totalRstr}건`}</div>
                                    <div className='rstr-sort-btns'>
                                        <SortIcon />
                                        <div className={sortOrder === 'distance' ? "rstr-sort-btn-clicked" : "rstr-sort-btn"}
                                            onClick={handleBasicSort}>기본순</div>
                                        <div className={sortOrder === 'rstr_review_rating' ? "rstr-sort-btn-clicked" : "rstr-sort-btn"}
                                            onClick={handleReviewSort}>리뷰높은순</div>
                                        <div className={sortOrder === 'rstr_review_count' ? "rstr-sort-btn-clicked" : "rstr-sort-btn"}
                                            onClick={handleReviewCountSort}>리뷰많은순</div>
                                        <div className={sortOrder === 'rstr_favorite_count' ? "rstr-sort-btn-clicked" : "rstr-sort-btn"}
                                            onClick={handleFavoriteSort}>찜많은순</div>
                                    </div>
                                </div>
                                <div style={{ height: '550px' }}>
                                    <RstrCards>
                                        {rstrList.map((rstrInfo) => (
                                            <RstrCard key={rstrInfo.rstr_id} rstrInfo={rstrInfo} mode={'rstrList'} />
                                        ))}
                                    </RstrCards>
                                </div>
                                <Pagination
                                    style={{ margin: '10px auto 0px' }}
                                    page={page}
                                    count={totalPage}
                                    color="primary"
                                    renderItem={(item) => (
                                        <PaginationItem
                                            component={Link}
                                            to={`/${search ?
                                                `rstr?page=${item.page}&search=${search}`
                                                : (item.page === 1 ? 'rstr' : `rstr?page=${item.page}`)}`}
                                            {...item}
                                        />
                                    )}
                                />
                            </>
                    )
                }
            </RstrListContainer>
        </RstrListPageContainer>
    )
}

export default RstrListPage;