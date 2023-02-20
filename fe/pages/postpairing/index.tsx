import Head from 'next/head';
import NavBar from '@/components/NavBar';
import BigInput from '@/components/inputs/BigInput';
import PairingBox from '@/components/selectBox/PairingBox';
import ImageUpload from '../../components/postPairingPage/ImageUpload';
import PostDetailCard from '@/components/postPairingPage/PostDetailCard';
import CloseBtn from '@/components/button/CloseBtn';
import SubmitBtn from '@/components/button/SubmitBtn';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { currentBeer } from '@/atoms/currentBeer';
import axios from 'axios';

export default function PostPairing() {
  const router = useRouter();
  const [beerInfo] = useRecoilState(currentBeer);
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('전체');
  const [isValid, setIsValid] = useState(false);
  const [jsonData, setJsonData] = useState({
    beerId: beerInfo.beerId,
    userId: 1,
    content: '',
    category: '',
  });
  const [finalData, setFinalData] = useState<any>();

  // userId 로직 짜야함
  let TOKEN =
    'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZW1haWwiOiJlMUBtYWlsLmNvbSIsInN1YiI6ImUxQG1haWwuY29tIiwiaWF0IjoxNjc2ODk4Mzc5LCJleHAiOjE2NzY5MDU1Nzl9.4REO_Y0M8aGeVmFY99qEpQ88zuY1s1buUF777hKVh1xrOGC1Y2uYnApvu9-VLUqEY_fKF_1DqGsGfMJVrBAlrw';
  const config = {
    headers: {
      Authorization: TOKEN,
      'content-type': 'multipart/form-data',
    },
  };

  useEffect(() => {
    if (content.length >= 3 && category !== '') setIsValid(true);
    else setIsValid(false);
  }, [content, category]);

  const handleSubmit = () => {
    setJsonData({
      beerId: beerInfo.beerId,
      userId: 1,
      content: content,
      category: category,
    });

    const formData = new FormData();
    formData.append(
      'post',
      new Blob([JSON.stringify(jsonData)], {
        type: 'application/json',
      })
    );
    formData.append('files', new Blob([JSON.stringify({})]));
    setFinalData(formData);

    // axios
    //   .post(`${process.env.API_URL}/pairings`, finalData, config)
    //   .then((response) => console.log(response))
    //   .catch((error) => console.log(error));
  };

  return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>

      <main className="m-auto h-screen max-w-4xl">
        <div className="p-5">
          <PostDetailCard beerInfo={beerInfo} />
          <div className="mt-6 mb-2 text-base font-semibold">
            페어링 카테고리
          </div>
          <PairingBox category={category} setCategory={setCategory} />
          <ImageUpload />
          <div className="mt-6 mb-2 text-base font-semibold">설명</div>
          <BigInput
            placeholder="추천 이유를 세글자 이상 적어주세요"
            inputState={content}
            setInputState={setContent}
          />
        </div>
        <div className="flex">
          <div className="flex-1">
            <CloseBtn onClick={() => router.back()}>나가기</CloseBtn>
          </div>
          <div className="flex-1">
            {isValid ? (
              <SubmitBtn onClick={handleSubmit}>등록하기</SubmitBtn>
            ) : (
              <div className="flex justify-center items-center w-full h-11 rounded-xl m-2 bg-red-100 text-xs text-red-500 -ml-[1px]">
                카테고리와 내용을 추가하세요
              </div>
            )}
          </div>
        </div>
        <NavBar />
      </main>
    </>
  );
}
