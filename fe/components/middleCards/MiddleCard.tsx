import Tag from '../Tag';
import Image from 'next/image';
import {
  BeerCategoryMatcherToKor,
  BeerCountryMatcherToKor,
} from '@/utils/BeerMatcher';

export interface MiddleCardInfo {
  beerId: number;
  thumbnail: string;
  korName: string;
  category: string[];
  country: string;
  abv: number;
  ibu: number | null;
  totalStarCount: number;
  totalAverageStars: number;
  beerTags: string[];
}

export default function MiddleCard({
  cardProps,
}: {
  cardProps: MiddleCardInfo;
}) {
  return (
    <div className="flex rounded-lg bg-white text-y-black border border-y-lightGray px-3 py-5 my-2">
      <div className="relative w-[130px] h-[160px]">
        <Image
          alt={cardProps?.korName}
          src={cardProps?.thumbnail}
          fill
          sizes="50vw"
          className="object-cover"
        />
      </div>
      <div className="flex flex-col justify-center">
        <h1 className="font-bold text-xl lg:text-2xl">{cardProps?.korName}</h1>
        <div className="text-xs sm:text-sm lg:text-lg">
          <span>
            {cardProps?.category?.map((el: string, idx: number) => {
              return (
                <span className="mx-0.5" key={idx}>
                  {BeerCategoryMatcherToKor(el)}
                </span>
              );
            })}
          </span>
          <span>/ {BeerCountryMatcherToKor(cardProps?.country)}</span>
          <span>/ {cardProps?.abv}%</span>
          {cardProps?.ibu ? <span>/ {cardProps?.ibu} IBU</span> : null}
        </div>
        <div className="my-2">
          <span className="font-semibold sm:text-xl lg:text-2xl">
            ⭐️ {cardProps?.totalAverageStars}
          </span>
          <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
            ({cardProps?.totalStarCount} ratings)
          </span>
        </div>
        <div className="flex flex-wrap">
          {cardProps?.beerTags.map((el: string, idx: number) => {
            return <Tag key={idx}>{el}</Tag>;
          })}
        </div>
      </div>
    </div>
  );
}
