const API = process.env.API_URL;

export default function NaverBtn() {
  const handleLogin = () => {
    return window.location.assign(`${API}/oauth2/authorization/naver`);
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
