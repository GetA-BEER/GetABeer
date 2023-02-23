import { FcGoogle } from 'react-icons/fc';
import { useRecoilState } from 'recoil';
import { accessToken } from '@/atoms/login';

const API = process.env.API_URL;

export default function GoogleBtn() {
  const handleLogin = () => {
    return window.location.assign(`${API}/oauth2/authorization/google`);
  };
  return (
    <div>
      <button
        onClick={handleLogin}
        className="flex justify-center items-center w-11 h-11 rounded-full border border-y-gray hover:bg-gray-200 text-xs"
      >
        <FcGoogle className="w-6 h-6" />
      </button>
    </div>
  );
}
