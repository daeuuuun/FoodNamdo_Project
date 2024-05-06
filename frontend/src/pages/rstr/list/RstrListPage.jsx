import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from "styled-components";
// import RstrList from './components/RstrList';
import palette from '../../../styles/palette';
import { Radio, FormControlLabel } from '@mui/material';
import { useFile } from '../../../data/FileContext';

import RstrCard from './components/RstrCard';
import { Link, useLocation } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';
import PaginationItem from '@mui/material/PaginationItem';
const RstrListPageContainer = styled.div`
    display: flex;
`;

const CategoryBarContainer = styled.div`
    width: 160px;
    margin: 40px 5px 0 0;
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

// 음식점 리스트
const RstrListContainer = styled.div`
    padding: 0 5px;
    width: 100%;
    // border: 1px solid gray;
    display: flex;
    flex-direction: column;
    .total-rstr-num {
        font-size: 0.8rem;
        // color: ${palette.darygray};
        margin: 10px 20px;
        text-align: right;
    }
    .rstr-loading {
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
    const [rstrList, setRstrList] = useState([]); // 음식점 리스트
    const [isLoading, setIsLoading] = useState(false);

    const [page, setPage] = useState(1);
    const [totalPage, setTotalPage] = useState(1);
    const [pageSize, setPageSize] = useState(1);
    const [totalRstr, setTotalRstr] = useState(1);
    const location = useLocation();

    // 체크박스 상태 관리하는 state (하나의 카테고리만 선택 가능)
    const [checkedRegion, setCheckedRegion] = useState('전체');
    const [checkedCategory, setCheckedCategory] = useState('전체');

    useEffect(() => {
        const query = new URLSearchParams(location.search);
        const newPage = parseInt(query.get('page') || '1', 10);
        setPage(newPage);
    }, [location]);

    // 이미지 파일로 검색 시
    useEffect(() => {
        const fetchDataWithFile = async () => {
            const formData = new FormData();
            formData.append('file', file);
            const params = new URLSearchParams({
                similarity: 1,
                n_results: 30,
                page_number: page,
                sort_order: 'distance',
                reverse: 'false',
                category: checkedCategory,
                region: checkedRegion,
            }).toString();

            const url = `http://foodnamdo.iptime.org:7999/image_to_image/?${params}`;

            try {
                const response = await axios.post(url, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    }
                });
                setRstrList(response.data.rstr); // 음식점 리스트 저장
                setTotalPage(response.data.total_pages);
                setPageSize(response.data.page_size);
                setTotalRstr(response.data.total_rstr);
                // random URL을 session에 저장하는 코드
                sessionStorage.setItem('random', response.data.random);
            } catch (error) {
                console.error('이미지 검색 에러:', error);
            }
            setIsLoading(false);
        };

        const fetchDataWithRandom = async () => {
            const savedRandom = sessionStorage.getItem('random');

            const params = new URLSearchParams({
                page_number: page,
                sort_order: 'distance',
                reverse: 'false',
                category: checkedCategory,
                region: checkedRegion,
            }).toString();

            const url = `http://foodnamdo.iptime.org:7999/${savedRandom}/?${params}`;

            try {
                const response = await axios.get(url);
                setRstrList(response.data.rstr); // 음식점 리스트 저장
                setTotalPage(response.data.total_pages);
                setPageSize(response.data.page_size);
                setTotalRstr(response.data.total_rstr);
            } catch (error) {
                console.error('이미지 검색 에러:', error);
            }
            setIsLoading(false);
        };

        if (file) {
            setIsLoading(true);
            console.log('파일 사용', file);
            fetchDataWithFile();
        } else {
            setIsLoading(true);
            console.log('파일 없음');
            fetchDataWithRandom();
        }
    }, [file, page, pageSize, checkedRegion, checkedCategory]);


    const regionOptions = [
        '전체', '경상남도', '전라남도'
    ];

    const categoryOptions = [
        '전체', '한식', '중식', '일식', '카페/제과점', '양식', '치킨/호프',
        '분식', '식육(숯불구이)', '회', '패스트푸드', '푸드트럭', '유원지', '전통차', '복어', '외국음식전문점', '아이스크림', '뷔페식', '기타'
    ];

    const handleRegionCheckboxChange = (e) => {
        setCheckedRegion(e.target.checked ? e.target.name : null);
    }

    const handleCheckboxChange = (e) => {
        setCheckedCategory(e.target.checked ? e.target.name : null);
    }

    useEffect(() => {
        // console.log(checkedCategory);
        // console.log(random);
    }, []);


    const handlePageChange = (value) => {
        setPage(value);
    };

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
                {isLoading ?
                    <div className='rstr-loading'>로딩중입니다. 잠시만 기다려주세요!</div> :
                    <>
                        <div style={{ height: '550px' }}>
                            <div className='total-rstr-num'>{`약 ${totalRstr}건`}</div>
                            <RstrCards>
                                {rstrList.map((rstrInfo) => (
                                    <RstrCard key={rstrInfo.rstr_id} rstrInfo={rstrInfo} />
                                ))}
                            </RstrCards>
                        </div>
                        <Pagination
                            style={{ margin: '0 auto' }}
                            page={page}
                            count={totalPage}
                            onChange={handlePageChange}
                            color="primary"
                            renderItem={(item) => (
                                <PaginationItem
                                    component={Link}
                                    to={`/rstr?page=${item.page}`}
                                    {...item}
                                />
                            )}
                        /></>
                }
            </RstrListContainer>
        </RstrListPageContainer>
    )
}

export default RstrListPage;