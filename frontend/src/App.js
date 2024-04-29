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
import IdAuthPage from './pages/auth/IdAuthPage';
import PwAuthPage from './pages/auth/PwAuthPage';
import UpArrow from './components/common/UpArrow';

function App() {
  return (
    <div className={'common-container'} style={{ fontFamily: 'Gmarket Sans Medium' }}>
      <Helmet>
        <title>푸드남도</title>
      </Helmet>
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />

          {/* 회원 인증 페이지들 */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/find-pw" element={<PwAuthPage />} />
          <Route path="/find-id" element={<IdAuthPage />} />

          {/* 음식점 페이지들 */}
          <Route path="/rstr/1" element={<RstrDetailPage />} />
          <Route path="/rstr" element={<RstrListPage />} />
        </Routes>
      </Router>
      {/* <UpArrow /> */}
    </div>
  );
}

export default App;
