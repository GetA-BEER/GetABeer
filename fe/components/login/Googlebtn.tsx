import { FcGoogle } from 'react-icons/fc';

export default function GoogleBtn() {
  const REST_API_KEY = process.env.NEXT_PUBLIC_GOOGLE_API_KEY;
  const REDIRECT_URI = 'http://localhost:3000/oauth/google';
  const link = `https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&scope=email%20profile`;
  const handleLogin = () => {
    return window.location.assign(link);
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
