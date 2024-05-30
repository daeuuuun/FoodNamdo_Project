import React, { useEffect, useState } from 'react';
import "./MyFavorite.css";
import RstrCard from './../rstr/list/components/RstrCard';
import { BACKEND_SERVER_URL } from '../../config/Config';
import axios from 'axios';
const MyFavorite = () => {

	const [rstrList, setRstrList] = useState([]);

	const getFavoriteRstr = async () => {
		const url = `${BACKEND_SERVER_URL}/mypage/getFavoriteRstr?page=${1}`;

		const token = localStorage.getItem('accessToken');

		try {
			const response = await axios.get(url,
				{
					headers: {
						'Authorization': `Bearer ${token}`
					}
				}
			);
			console.log(response.data);
			setRstrList(response.data.rstr);

		} catch (error) {
			console.log(error);
		}
	}

	useEffect(() => {
		getFavoriteRstr();
	}, [])

	return (
		<div className='my-favorite-container'>
			{rstrList.length > 0 ? (
				rstrList.map((rstrInfo, index) => (
					<RstrCard key={index} rstrInfo={rstrInfo} />
				))
			) : (
				<div>즐겨찾는 음식점이 없습니다.</div>
			)}
		</div>
	)
}

export default MyFavorite;