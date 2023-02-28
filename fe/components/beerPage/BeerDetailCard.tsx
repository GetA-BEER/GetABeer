import {
  BeerCountryMatcherToKor,
  BeerCategoryMatcherToKor,
} from '@/utils/BeerMatcher';
import Image from 'next/image';
import Link from 'next/link';
import { HiPencil, HiChartPie } from 'react-icons/hi';
import SmallTag from '../smallCards/SmallTag';
import WishHeart from '@/components/WishHeart';
import StarScore from './StarScore';
import ShareBtn from '../share/ShareBtn';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import Swal from 'sweetalert2';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';

export default function BeerDetailCard({ cardProps }: any) {
  const [isWish, setIsWish] = useState<boolean>(cardProps?.isWishlist);
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState(false);
  const router = useRouter();
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  useEffect(() => {
    axios.get;
  });

  const goToLogin = () => {
    Swal.fire({
      text: '로그인이 필요한 서비스 입니다.',
      showCancelButton: true,
      confirmButtonColor: '#f1b31c',
      cancelButtonColor: '#A7A7A7',
      confirmButtonText: '로그인',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        router.push({
          pathname: '/login',
        });
      }
    });
  };

  return (
    <div className="flex rounded-xl bg-white text-y-black border border-y-lightGray py-2 my-2 relative">
      <div className="flex m-auto">
        <div className="w-[122px] select-none">
          {cardProps?.beerDetailsBasic.thumbnail.includes('.') ? (
            <Image
              className="pt-3 w-full h-auto select-none"
              alt={cardProps?.beerDetailsBasic.korName}
              src={cardProps?.beerDetailsBasic.thumbnail}
              width={100}
              height={100}
              priority
            />
          ) : (
            <>x</>
          )}
        </div>
        <div className="flex flex-col justify-center">
          <div className="flex items-start">
            <h1 className="font-bold text-2xl break-keep">
              {cardProps?.beerDetailsBasic.korName}
            </h1>
            <WishHeart
              beerId={cardProps?.beerId}
              isWish={isWish}
              setIsWish={setIsWish}
              isLogin={isLogin}
            />
          </div>
          <div className="text-xs flex flex-wrap">
            <span>
              {cardProps?.beerCategoryTypes.map((el: string, idx: number) => {
                return (
                  <span className="mr-0.5" key={idx}>
                    {BeerCategoryMatcherToKor(el)}
                  </span>
                );
              })}
            </span>
            <span className="mx-[1px]">
              / {BeerCountryMatcherToKor(cardProps?.beerDetailsBasic.country)}
            </span>
            <span className="mx-[1px]">{cardProps?.beerDetailsBasic.abv}%</span>
            {cardProps?.beerDetailsBasic.ibu !== null ? (
              <span>/ {cardProps?.beerDetailsBasic.ibu} IBU</span>
            ) : (
              <></>
            )}
          </div>

          <div className="flex items-end">
            <StarScore score={cardProps?.beerDetailsStars.totalAverageStars} />
            <span className="text-xs text-y-gray">
              ({cardProps?.beerDetailsStars.totalAverageStars})
            </span>
          </div>
          <div className="mb-0.5 text-xs flex">
            <div className="mr-4">
              <Image
                src="/images/star.png"
                alt="star"
                width={13}
                height={13}
                className="mr-1 mb-[3px] text-y-gold drop-shadow-md inline"
              />
              {cardProps?.beerDetailsStars.femaleAverageStars} 여성
            </div>
            <div>
              <Image
                src="/images/star.png"
                alt="star"
                width={13}
                height={13}
                className="mr-1 mb-[3px] text-y-gold drop-shadow-md inline"
              />
              {cardProps?.beerDetailsStars.maleAverageStars} 남성
            </div>
          </div>
          {cardProps?.beerDetailsTopTags === null ? (
            <></>
          ) : (
            <SmallTag tags={cardProps?.beerDetailsTopTags} />
          )}
          <div className="text-xs">
            {isLogin ? (
              <Link href={'/postrating'} className="hover:text-y-gold mr-1">
                <HiPencil className="inline" /> 평가하기
              </Link>
            ) : (
              <span onClick={goToLogin} className="hover:text-y-gold mr-1">
                <HiPencil className="inline" /> 평가하기
              </span>
            )}
            {isLogin ? (
              <Link href={'/postpairing'} className="hover:text-y-gold">
                <span className="mr-1">
                  <HiChartPie className="inline" /> 페어링
                </span>
              </Link>
            ) : (
              <span onClick={goToLogin} className="hover:text-y-gold mr-1">
                <HiChartPie className="inline" /> 페어링
              </span>
            )}

            <ShareBtn />
          </div>
        </div>
      </div>
    </div>
  );
}
