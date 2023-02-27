import Image from 'next/image';
import { useEffect, useState } from 'react';
import WishHeart from '../WishHeart';
import {
  BeerCountryMatcherToKor,
  BeerCategoryMatcherToKor,
} from '@/utils/BeerMatcher';
export default function WishCard({ wishProps, idx }: any) {
  const [isWish, setIsWish] = useState<any>(wishProps.isUserWish);
  const [wishInfo, setWishInfo] = useState<any>();
  useEffect(() => {
    setWishInfo(wishProps.beer);
  }, [wishProps.beer]);

  return (
    <div className="rounded-2xl max-w-4xl bg-white text-y-black drop-shadow-xl text-[5px] border">
      <div
        className={`${
          idx % 4 === 1 || idx % 4 === 0 ? 'bg-y-cream' : 'bg-y-lemon'
        } p-4 rounded-t-2xl`}
      >
        <div className="flex justify-between">
          <div className="text-base font-semibold">{wishInfo?.korName}</div>
          <WishHeart
            beerId={wishInfo?.beerId}
            isWish={isWish}
            setIsWish={setIsWish}
          />
        </div>
        <div className="-mt-2">
          <span>
            {wishInfo?.beerCategories.map((el: any, idx: number) => (
              <span className="mr-0.5" key={idx}>
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
        <Image
          className="pt-3 rounded-2xl m-auto"
          alt="Beer"
          src={`${wishInfo?.thumbnail}`}
          width={300}
          height={200}
        />
      )}
    </div>
  );
}
