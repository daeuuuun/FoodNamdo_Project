import React from 'react';
import { Helmet } from "react-helmet";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './index.css';
import '../src/styles/common.css';
import MainPage from './pages/main/MainPage';
import LoginPage from './pages/auth/LoginPage';
import SignUpPage from './pages/auth/SignUpPage';
import RstrDetailPage from './pages/rstr/detail/RstrDetailPage';
import RstrListPage from './pages/rstr/list/RstrListPage';
import FindIdPage from './pages/auth/FindIdPage';
import FindPwPage from './pages/auth/FindPwPage';
import { FileProvider } from './data/FileContext';
import Header from './pages/header/Header';
import MyPage from "./pages/myPage/MyPage";
import ReviewInsert from "./pages/review/ReviewInsert";
import ResetPwPage from './pages/auth/ResetPwPage';
import ReviewUpdate from "./pages/review/ReviewUpdate";

function App() {
  return (
    <div className={'common-container'} style={{ fontFamily: 'Gmarket Sans Medium' }}>
      <Helmet>
        <title>푸드남도</title>
      </Helmet>
      <FileProvider>
        <Router>
          <Header />
          <Routes>
            <Route path="/" element={<MainPage />} />

            {/* 회원 인증 페이지들 */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/find-id" element={<FindIdPage />} />
            <Route path="/find-pw" element={<FindPwPage />} />
            <Route path='/reset-pw' element={<ResetPwPage />} />

            {/* 음식점 페이지들 */}
            <Route path="/rstr/:rstrId" element={<RstrDetailPage />} />
            <Route path="/rstr/:page?" element={<RstrListPage />} />

            <Route path="/mypage" element={<MyPage />} />

            <Route path="/review/insert/:id" element={<ReviewInsert />} />
            <Route path="/review/update" element={<ReviewUpdate />} />
          </Routes>
        </Router>
      </FileProvider>
    </div>
  );
}

export default App;