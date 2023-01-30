import Image from 'next/image';
import NavBar from '@/components/NavBar';
import SmallCardController from '@/components/SmallCardController';
// import React, { useState } from 'react';
export interface SmallCardInfo {
  id: number;
  star: number;
  nickName: string;
  description: string;
  date: string;
  comments: number;
  thumbs: number;
}

export default function Main() {
  // const [smallCardList, setSmallCardList] = useState<SmallCardInfo[]>([
  //   {
  //     id: 1,
  //     star: 4.0,
  //     nickName: '유진님',
  //     description:
  //       '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
  //     date: '2023.41.30',
  //     comments: 5,
  //     thumbs: 10,
  //   },
  //   {
  //     id: 2,
  //     star: 4.0,
  //     nickName: '테스트',
  //     description:
  //       '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
  //     date: '2023.41.30',
  //     comments: 5,
  //     thumbs: 10,
  //   },
  //   {
  //     id: 3,
  //     star: 4.0,
  //     nickName: '어렵네',
  //     description:
  //       '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
  //     date: '2023.41.30',
  //     comments: 5,
  //     thumbs: 10,
  //   },
  // ]);

  return (
    <div className="h-screen m-auto max-w-4xl">
      <div>
        <main className="m-auto border-2 pb-14">
          <div className="py-2 bg-gray-200 text-black">상단헤더</div>
          <Image
            className="m-auto"
            src="/images/adv.jpg"
            alt="adv"
            width={500}
            height={500}
            priority
          />
          <div className="m-auto border">
            <SmallCardController />
          </div>
        </main>
        <NavBar />
      </div>
    </div>
  );
}
