import { RiKakaoTalkFill } from 'react-icons/ri';

const API = process.env.API_URL;

export default function KakaoBtn() {
  const handleLogin = () => {
    return window.location.assign(`${API}/oauth2/authorization/kakao`);
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
