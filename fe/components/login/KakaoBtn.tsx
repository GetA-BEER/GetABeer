import { RiKakaoTalkFill } from 'react-icons/ri';

const REST_API_KEY = process.env.NEXT_PUBLIC_KAKAO_REST_API_KEY;
const REDIRECT_URI = 'https://www.getabeer.co.kr/oauth/kakao';
const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
export default function KakaoBtn() {
  const handleLogin = () => {
    return window.location.assign(link);
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
