import Tag from '../Tag';
import Image from 'next/image';
import { BsChatDots } from 'react-icons/bs';

export interface MonthlyCardProps {
  beerId: number;
  korName: string;
  beerDetailsTopTags: [string, string, string, string] | null;
  totalAverageStars: number;
  totalStarCount: number;
  thumbnail: string;
  bestRating: {
    bestRatingId: number;
    bestNickname: string;
    profileImage: string;
    bestStar: number;
    bestContent: string;
  } | null;
}

export default function MonthlyCard({
  cardProps,
  idx,
}: {
  cardProps: MonthlyCardProps;
  idx: number;
}) {
  return (
    <div className="flex flex-col rounded-lg bg-white text-y-black border border-y-lightGray m-2">
      <div className="flex">
        <div className="flex justify-center items-center w-6 h-6 rounded-[5px] bg-y-lightGray z-[5] m-1">
          <span className="text-white">{idx + 1}</span>
        </div>
        <div className="relative w-[120px] h-[150px]">
          <Image
            alt={cardProps?.korName}
            src={cardProps?.thumbnail}
            fill
            className="rounded-lg object-none object-top "
          />
        </div>
        <div className="py-4">
          <h1 className="font-bold text-xl lg:text-2xl">
            {cardProps?.korName}
          </h1>
          <div className="my-2">
            <span className="text-xl sm:text-2xl lg:text-3xl">
              ⭐️ {cardProps?.totalAverageStars}
            </span>
            <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
              ({cardProps?.totalStarCount} ratings)
            </span>
          </div>
          <div className="flex flex-wrap">
            {cardProps?.beerDetailsTopTags
              ? cardProps?.beerDetailsTopTags.map((el: string, idx: number) => {
                  return <Tag key={idx}>{el}</Tag>;
                })
              : null}
          </div>
        </div>
      </div>
      <div>
        <div className="flex border-t border-y-lightGray">
          <div className="my-4">
            <BsChatDots className="ml-4" />
          </div>
          {cardProps?.bestRating ? (
            <div className="flex flex-col text-sm my-4 mx-2">
              <div className="text-xs sm:text-sm lg:text-lg">
                <span>⭐️ {cardProps.bestRating?.bestStar} </span>
                <span>{cardProps.bestRating?.bestContent}</span>
              </div>
              <div className="flex justify-end mt-1 mr-2 text-xs sm:text-sm lg:text-lg">
                <div className="rounded-full w-5 h-5 mr-1">
                  <Image
                    alt={'user profile image'}
                    src={cardProps?.bestRating?.profileImage}
                    fill
                  />
                </div>
                <span>{cardProps.bestRating?.bestNickname}</span>
              </div>
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
}
