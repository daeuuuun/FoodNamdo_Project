import React, { useState, useEffect } from 'react';
import styled from "styled-components";
import RstrList from './components/RstrList';
import palette from '../../../styles/palette';
import { Radio, FormControlLabel } from '@mui/material';

const RstrListPageContainer = styled.div`
    display: flex;
`;

const CategoryBarContainer = styled.div`
    width: 15%;
    margin: 40px 5px 0 0;
    padding: 10px 15px;
    height: 290px;
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
        margin-top: 10px;
        font-size: 0.8rem;
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



export const RstrListPage = () => {

    const categoryOptions = [
        '한식', '중식', '일식', '양식', '음료류', '제과류', '패스트푸드', '기타'
    ];

    // 체크박스 상태 관리하는 state (하나의 카테고리만 선택 가능)
    const [checkedCategory, setCheckedCategory] = useState(null);

    const handleCheckboxChange = (e) => {
        setCheckedCategory(e.target.checked ? e.target.name : null);
    }

    useEffect(() => {
        console.log(checkedCategory);
    }, [checkedCategory]);

    return (
        <RstrListPageContainer>
            <CategoryBarContainer>
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
            <RstrList></RstrList>
        </RstrListPageContainer>
    )
}

export default RstrListPage;