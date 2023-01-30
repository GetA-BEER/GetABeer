import Head from 'next/head';
import Advertise from './Advertise';
import Image from 'next/image';
import NavBar from '@/components/NavBar';
import SmallCardController from '@/components/smallCards/SmallCardController';
import SmallpairingController from '@/components/smallCards/SmallpairingController';
import BigInput from '@/components/inputs/BigInput';
import SmallBeerController from '@/components/smallCards/SmallBeerController';

export default function Main() {
  const cardProps = [
    {
      id: 1,
      star: 4.0,
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 2,
      star: 4.0,
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
  ];
  const pairingProps = [
    {
      id: 1,
      pairing: '튀김',
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 2,
      pairing: '구이',
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 3,
      pairing: '견과류',
      nickName: '어렵네',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
  ];
  const beerProps = [
    {
      id: 1,
      title: '가든 바이젠',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    },
    {
      id: 2,
      title: '필라이트',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2211160004_R.jpg',
    },
    {
      id: 3,
      title: '가든 바이젠',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2011190018_M.jpg',
    },
    {
      id: 4,
      title: '가든 바이젠',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    },
    {
      id: 5,
      title: '필라이트',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2211160004_R.jpg',
    },
  ];
  https: return (
    <>
      <Head>
        <title>GetABeer</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/images/logo.png" />
      </Head>
      <div className="h-screen m-auto max-w-4xl">
        <div>
          <main className="m-auto border-2">
            <div className="py-2 bg-gray-200 text-black">상단헤더</div>
            <BigInput placeholder="페어링을 추천하는 이유를 적어주세요" />
            <Advertise />
            <div className="m-auto">
              레이팅 소 카드
              <SmallCardController cardProps={cardProps} />
              페어링 소 카드
              <SmallpairingController pairingProps={pairingProps} />
              작은 맥주 카드
              <SmallBeerController beerProps={beerProps} />
            </div>
          </main>
          <div className="pb-32"></div>
          <NavBar />
        </div>
      </div>
    </>
  );
}
