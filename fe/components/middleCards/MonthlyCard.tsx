import Tag from '../Tag';
import Image from 'next/image';

export default function MonthlyCard(props: { cardProps: any; idx: number }) {
  return (
    <div className="flex rounded-lg text-y-black border border-y-lightGray m-2">
      <div className="flex-none">
        <div className="flex justify-center items-center w-6 h-6 rounded-[5px] bg-y-lightGray z-10 absolute m-1">
          <span className="text-white">{props.idx + 1}</span>
        </div>
        <Image
          className="pt-6"
          alt={props.cardProps?.korName}
          src={props.cardProps?.thumbnail}
          width={120}
          height={120}
        />
      </div>
      <div
        className={`${
          props.idx % 2 === 0 ? 'bg-y-cream' : 'bg-y-lemon'
        } flex-auto flex justify-center py-4`}
      >
        <div className="flex flex-col justify-center items-center">
          <h1 className="mb-2 font-bold text-2xl sm:text-3xl lg:text-4xl">
            {props.cardProps?.korName}
          </h1>
          <div className="text-xs sm:text-sm lg:text-lg">
            <span>{props.cardProps?.country}</span>
            <span>
              /
              {props.cardProps?.category.map((el: string, idx: number) => {
                return (
                  <span className="mx-0.5" key={idx}>
                    {el}
                  </span>
                );
              })}
            </span>
            <span>/ {props.cardProps?.abv}%</span>
            <span>/ {props.cardProps?.ibu} IBU</span>
          </div>
          <div className="my-4">
            <span className="font-semibold sm:text-xl lg:text-2xl">
              ⭐️ {props.cardProps?.totalAverageStars}
            </span>
            <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
              ({props.cardProps?.totalStarCount} ratings)
            </span>
          </div>
          <div>
            {props.cardProps?.beerTags.map((el: string, idx: number) => {
              return <Tag key={idx}>{el}</Tag>;
            })}
          </div>
        </div>
      </div>
    </div>
  );
}
