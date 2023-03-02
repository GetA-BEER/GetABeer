import { useEffect } from 'react';
import { RiKakaoTalkFill } from 'react-icons/ri';
import axios from '@/pages/api/axios';
const API = process.env.API_URL;

export default function KakaoBtn() {
  const handleLogin = () => {
    // axios
    //   .post(`${API}/oauth2/authorization/kakao`)
    //   .then((res) => {
    //     console.log(res);
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //   });
    return window.location.assign(
      `http://localhost:8080/oauth2/authorization/kakao`
    );
  };

  return (
    <div>
      <button
        onClick={handleLogin}
        className="flex justify-center items-center w-11 h-11 rounded-full bg-yellow-400 hover:bg-yellow-200 text-xs"
      >
        <RiKakaoTalkFill className="w-6 h-6" />
      </button>
    </div>
  );
}
