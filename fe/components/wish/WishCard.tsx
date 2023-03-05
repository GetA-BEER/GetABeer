import Image from 'next/image';
import { useEffect, useState } from 'react';
import WishHeart from '../WishHeart';
import {
  BeerCountryMatcherToKor,
  BeerCategoryMatcherToKor,
} from '@/utils/BeerMatcher';
import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import Link from 'next/link';

export default function WishCard({ wishProps, idx }: any) {
  const [isWish, setIsWish] = useState<any>(wishProps.isUserWish);
  const [wishInfo, setWishInfo] = useState<any>();
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  useEffect(() => {
    setWishInfo(wishProps.beer);
  }, [wishProps.beer]);

  return (
    <div className="rounded-2xl max-w-4xl bg-white text-y-black drop-shadow-xl text-xs border">
      <div
        className={`${
          idx % 4 === 1 || idx % 4 === 0 ? 'bg-y-cream' : 'bg-y-lemon'
        } p-4 rounded-t-2xl`}
      >
        <div className="flex justify-between">
          <div className="text-base font-semibold truncate">
            {wishInfo?.korName}
          </div>
          <WishHeart
            isLogin={isLogin}
            beerId={wishInfo?.beerId}
            isWish={isWish}
            setIsWish={setIsWish}
          />
        </div>
        <div className="-mt-2">
          <span>
            {wishInfo?.beerCategories.map((el: any, idx: number) => (
              <span key={idx}>
                {BeerCategoryMatcherToKor(el?.beerCategoryType)}/
              </span>
            ))}
          </span>
          <span>{BeerCountryMatcherToKor(wishInfo?.country)}</span>
          {wishInfo?.ibu === null ? <></> : <span>{wishInfo?.ibu}IBU</span>}
          <span>{wishInfo?.abv}%</span>
        </div>
      </div>
      {wishInfo?.thumbnail === undefined ? (
        <></>
      ) : (
        <Link href={`/beer/${wishInfo?.beerId} `}>
          <Image
            className="pt-3 rounded-2xl m-auto"
            alt="Beer"
            src={`${wishInfo?.thumbnail}`}
            width={300}
            height={200}
          />
        </Link>
      )}
    </div>
  );
}
