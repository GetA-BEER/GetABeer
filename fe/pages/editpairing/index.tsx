import Head from 'next/head';
import NavBar from '@/components/NavBar';
import BigInput from '@/components/inputs/BigInput';
import PairingBox from '@/components/selectBox/PairingBox';
import ImageUpload from '../../components/postPairingPage/ImageUpload';
import { useState } from 'react';


export default function EditPairing() {
  const [content, setContent] = useState('');

  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>

      <main className="m-auto h-screen max-w-4xl ">
        <div className="p-5">
          <div className="mt-6 mb-2 text-base font-semibold">
            페어링 카테고리
          </div>
          <PairingBox />
        <div className="mt-6 mb-2 text-base font-semibold">설명</div>
        <BigInput
          placeholder="페어링을 추천하시는 이유를 적어주세요"
          inputState={content}
          setInputState={setContent}
        />
        <ImageUpload />

        <NavBar />
      </main>
    </>
  );
}
