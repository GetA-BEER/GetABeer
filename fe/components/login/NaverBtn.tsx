const API = process.env.API_URL;

export default function NaverBtn() {
  const REST_API_KEY = process.env.NEXT_PUBLIC_NAVER_API_KEY;
  const REDIRECT_URI = 'http://localhost:3000/oauth/naver';
  const state = Math.random().toString(36).substr(3, 14);
  const link = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&state=${state}`;
  const handleLogin = () => {
    return window.location.assign(link);
  };
  return (
    <div>
      <button
        onClick={handleLogin}
        className="flex justify-center items-center w-11 h-11 rounded-full bg-green-500 hover:bg-green-700 text-xs"
      >
        <div className="w-6 h-6 text-white text-center text-xl font-black leading-6">
          N
        </div>
      </button>
    </div>
  );
}
