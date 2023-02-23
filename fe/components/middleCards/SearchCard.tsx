import Tag from '../Tag';
import Image from 'next/image';
import {
  BeerCategoryMatcherToKor,
  BeerCountryMatcherToKor,
} from '@/utils/BeerMatcher';

export interface SearchCardProps {
  beerId: number;
  korName: string;
  country: string;
  category: string[];
  abv: number | null;
  ibu: number;
  beerDetailsTopTags: [string, string, string, string] | null;
  totalAverageStars: number;
  totalStarCount: number;
  thumbnail: string;
}

export default function SearchCard(props: {
  cardProps: SearchCardProps;
  idx: number;
}) {
  return (
    <div className="flex rounded-lg text-y-black border border-y-lightGray m-2 min-h-[160px]">
      <div className="flex-none">
        <div className="relative w-[130px] h-[160px]">
          <Image
            alt={props.cardProps?.korName}
            src={props.cardProps?.thumbnail}
            fill
            className="object-cover rounded-lg"
          />
        </div>
      </div>
      <div
        className={`${
          props.idx % 2 === 0 ? 'bg-y-cream' : 'bg-y-lemon'
        } flex-auto flex justify-center py-4 rounded-r-lg`}
      >
        <div className="flex flex-col justify-center items-center">
          <h1 className="mb-2 font-bold text-xl lg:text-2xl">
            {props.cardProps?.korName}
          </h1>
          <div className="text-xs sm:text-sm lg:text-lg">
            <span>
              {props.cardProps?.category.map((el: string, idx: number) => {
                return (
                  <span className="mx-0.5" key={idx}>
                    {BeerCategoryMatcherToKor(el)}
                  </span>
                );
              })}
            </span>
            <span>/ {BeerCountryMatcherToKor(props.cardProps?.country)}</span>
            <span>/ {props.cardProps?.abv}%</span>
            {props.cardProps?.ibu ? (
              <span>/ {props.cardProps?.ibu} IBU</span>
            ) : null}
          </div>
          <div className="my-4">
            <span className="font-semibold sm:text-xl lg:text-2xl">
              ⭐️ {props.cardProps?.totalAverageStars}
            </span>
            <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
              ({props.cardProps?.totalStarCount} ratings)
            </span>
          </div>
          <div className="flex flex-wrap ml-2">
            {props.cardProps?.beerDetailsTopTags
              ? props.cardProps?.beerDetailsTopTags.map(
                  (el: string, idx: number) => {
                    return <Tag key={idx}>{el}</Tag>;
                  }
                )
              : null}
          </div>
        </div>
      </div>
    </div>
  );
}
