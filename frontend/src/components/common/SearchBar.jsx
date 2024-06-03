import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import palette from '../../styles/palette';
import SearchIcon from '@mui/icons-material/Search';
import ImageSearchIcon from '@mui/icons-material/ImageSearch';
//모달창 관련
import PhotoSizeSelectActualOutlinedIcon from '@mui/icons-material/PhotoSizeSelectActualOutlined';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useFile } from '../../data/FileContext';

const theme = createTheme({
    typography: {
        fontFamily: 'Gmarket Sans Medium',
    },
});

const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 560,
    height: 330,
    bgcolor: 'background.paper',
    boxShadow: 24,
    borderRadius: '10px',
    p: 3,
};

const modalContentStyle = {
    border: `2px dotted ${palette.gray}`,
    borderRadius: '15px',
    backgroundColor: `${palette.lightgray}`,
    width: '90%',
    height: '80%',
    color: `${palette.darygray}`,
    fontSize: '1rem',
    cursor: 'pointer',
    marginTop: '10px',
}

const closeButtonStyle = {
    position: 'absolute',
    top: 10,
    right: 15,
    cursor: 'pointer',
};

const Container = styled.div`
    display: flex;
    align-items: center;
    padding: 5px 10px;
    border: 2px solid ${palette.blue};
    border-radius: 20px;
    width: 320px;
    height: 28px;
    background-color: #ffffff;

    input {
        font-family: 'Gmarket Sans Medium';
        width: 90%;
        height: 100%;
        border: none;
        outline: none;
        font-size: 1rem;
    }
`

const StyledSearchIcon = styled(SearchIcon)`
    color: ${palette.blue};
    font-size: 1.1rem;
    margin-left: 5px;
    &:hover {
        cursor: pointer;
    }
`

const StyledImageSearchIcon = styled(ImageSearchIcon)`
    color: ${palette.blue};
    font-size: 1.1rem;
    &:hover {
        cursor: pointer;
    }
`

const SearchBar = () => {
    const navigate = useNavigate();
    const [search, setSearch] = useState();
    const fileInputRef = useRef(null);
    const searchInputRef = useRef(null);
    const { setFile } = useFile(); // 등록된 이미지 파일

    // 모달창 관련
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    useEffect(() => {
        searchInputRef.current.focus();
    });

    const handleSearch = (e) => {
        setSearch(e.target.value);
    }

    // 텍스트 검색
    const handleClickSearch = async () => {
        if (!search) {
            return;
        }

        sessionStorage.removeItem('random');
        setFile(null);
        navigate(`/rstr?search=${search}`);
        setSearch('');
    }

    // 이미지 검색
    const handleClickImageSearch = async (e) => {
        let file;
        if (e && e.target.files && e.target.files.length > 0) {
            file = e.target.files[0];
        }
        if (file) {
            console.log(file);
            setFile(file);
            navigate('/rstr');
        }
        console.log('이미지 검색입니다');
        handleClose();
    }

    return (
        <ThemeProvider theme={theme}>
            <Container>
                <input
                    value={search}
                    onChange={handleSearch}
                    onKeyPress={(e) => {
                        if (e.key === 'Enter') {
                            handleClickSearch();
                        }
                    }}
                    ref={searchInputRef}
                />
                <StyledSearchIcon
                    onClick={handleClickSearch}
                />
                <StyledImageSearchIcon onClick={handleOpen} />
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box className='centered-flex' sx={modalStyle}>
                        <Box sx={closeButtonStyle} onClick={handleClose}>
                            <CloseRoundedIcon />
                        </Box>
                        <Typography id="modal-modal-title" variant="h5" component="h2"
                            sx={{ color: palette.darkblue2, fontWeight: 'bold' }}
                        >
                            이미지 검색
                        </Typography>
                        <Typography id="modal-modal-description" component="h2"
                            sx={{ fontSize: '1rem' }}>
                            음식점/리뷰 이미지로 비슷한 음식점을 검색해보세요!
                        </Typography>
                        <Typography className='centered-flex' id="modal-modal-description" sx={modalContentStyle}
                            onClick={() => fileInputRef.current.click()}
                        >
                            <input
                                ref={fileInputRef}
                                type="file"
                                style={{ display: 'none' }}
                                accept="image/jpeg, image/jpg, image/png"
                                onChange={(e) => handleClickImageSearch(e)}
                            />
                            <PhotoSizeSelectActualOutlinedIcon sx={{ fontSize: '2.6rem', color: palette.lightblue2, marginBottom: '5px' }} />
                            <div>파일을 업로드해주세요!</div>
                        </Typography>
                    </Box>
                </Modal>
            </Container>
        </ThemeProvider >
    )
}

export default SearchBar;