import React from 'react';
import { Helmet } from "react-helmet";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './index.css';
import '../src/styles/common.css';
import '../src/styles/authform.css';
import MainPage from './pages/main/MainPage';
import LoginPage from './pages/auth/LoginPage';
import SignUpPage from './pages/auth/SignUpPage';
import RstrDetailPage from './pages/rstr/detail/RstrDetailPage';
import RstrListPage from './pages/rstr/list/RstrListPage';
import UpArrow from './components/common/UpArrow';
import FindIdPage from './pages/auth/FindIdPage';
import FindPwPage from './pages/auth/FindPwPage';
import SearchBar from './components/common/SearchBar';
import { FileProvider } from './data/FileContext'; // 이미지 파일 전역 관리

function App() {
  return (
    <div className={'common-container'} style={{ fontFamily: 'Gmarket Sans Medium' }}>
      <Helmet>
        <title>푸드남도</title>
      </Helmet>
      <Router>
        <Routes>
          <Route path="/" element={
            <FileProvider>
              <MainPage />
            </FileProvider>
          } />

          {/* 회원 인증 페이지들 */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/find-pw" element={<FindPwPage />} />
          <Route path="/find-id" element={<FindIdPage />} />

          {/* 음식점 페이지들 */}
          <Route path="/rstr/1" element={<RstrDetailPage />} />
          <Route path="/rstr/:page?" element={
            <FileProvider>
              <RstrListPage />
            </FileProvider>
          } />
        </Routes>
      </Router>
      {/* <UpArrow /> */}
    </div>
  );
}

export default App;
