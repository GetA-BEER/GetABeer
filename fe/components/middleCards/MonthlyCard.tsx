import Tag from '../Tag';
import Image from 'next/image';

export default function MonthlyCard({ cardProps }: any) {
  return (
    <div className="flex rounded-lg text-y-black border border-y-lightGray m-2">
      <div className="flex-none">
        <Image
          className="pt-3"
          alt={cardProps?.korName}
          src={cardProps?.thumbnail}
          width={120}
          height={120}
        />
      </div>
      <div className="flex-auto flex justify-center bg-y-cream py-4">
        <div className="flex flex-col justify-center items-center">
          <h1 className="mb-2 font-bold text-2xl sm:text-3xl lg:text-4xl">
            {cardProps?.korName}
          </h1>
          <div className="text-xs sm:text-sm lg:text-lg">
            <span>{cardProps?.country}</span>
            <span>
              /
              {cardProps?.category.map((el: string, idx: number) => {
                return (
                  <span className="mx-0.5" key={idx}>
                    {el}
                  </span>
                );
              })}
            </span>
            <span>/ {cardProps?.abv}%</span>
            <span>/ {cardProps?.ibu} IBU</span>
          </div>
          <div className="my-4">
            <span className="font-semibold sm:text-xl lg:text-2xl">
              ⭐️ {cardProps?.totalAverageStars}
            </span>
            <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
              ({cardProps?.totalStarCount} ratings)
            </span>
          </div>
          <div>
            {cardProps?.beerTags.map((el: string, idx: number) => {
              return <Tag key={idx}>{el}</Tag>;
            })}
          </div>
        </div>
      </div>
    </div>
  );
}
