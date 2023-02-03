import SubmitBtn from '@/components/button/SubmitBtn';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/inputs/Input';
import Head from 'next/head';
import { IoClose } from 'react-icons/io5';
import KakaoBtn from '@/components/login/kakaoBtn';
import NaverBtn from '@/components/login/NaverBtn';
import GoogleBtn from '@/components/login/Googlebtn';

export default function Login() {
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {};
  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>
      <main className="m-auto h-screen max-w-md">
        <button className="m-4">
          <IoClose className="w-6 h-6" />
        </button>
        <div className="my-6 text-center text-lg bg-white rounded-lg font-semibold">
          로그인
        </div>
        <div className="mx-2 my-4">
          <Input type="email" placeholder="email@gmail.com" />
          <Input type="password" placeholder="비밀번호를 입력하세요." />
          <SubmitBtn onClick={handleClick}> 로그인 </SubmitBtn>
          <div className="my-3 flex justify-center gap-1.5 text-sm">
            <div className="text-y-gray font-light">만약 계정이 없다면?</div>
            <button className="flex text-y-brown">회원가입</button>
          </div>
        </div>
        <div className="flex items-center justify-center space-x-2 my-6">
          <span className="h-px w-3/12  bg-gray-200"></span>
          <span className="text-y-gray text-sm font-light">
            소셜 계정 로그인
          </span>
          <span className="h-px w-3/12 bg-gray-200"></span>
        </div>
        <div className="flex justify-center gap-5">
          <KakaoBtn onClick={handleClick} />
          <NaverBtn onClick={handleClick} />
          <GoogleBtn onClick={handleClick} />
        </div>
        <NavBar />
      </main>
    </>
  );
}