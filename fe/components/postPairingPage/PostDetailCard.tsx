import Tag from '../Tag';
import Image from 'next/image';
import { useEffect, useState } from 'react';

export default function PostDetailCard(props: any) {
  const [beerInfo, setBeerInfo] = useState(props);
  useEffect(() => {
    if (props.beerInfo !== undefined) setBeerInfo(props.beerInfo);
  }, [props]);

  return (
    <div className="flex rounded-lg bg-white text-y-black border border-y-lightGray px-3 py-5 my-2">
      {/* 이거 나중에 제대로 썸네일 들어오면  삭제해야 한다. */}
      {beerInfo?.beerDetailsBasic?.thumbnail.includes('.') ? (
        <Image
          className="pt-3 w-[100px] h-auto"
          alt={beerInfo?.beerDetailsBasic?.korName}
          src={beerInfo?.beerDetailsBasic?.thumbnail}
          width={100}
          height={200}
        />
      ) : (
        <Image
          className="pt-3 w-[100px] h-auto"
          alt="임시이미지"
          src="https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg"
          width={100}
          height={200}
          priority
        />
      )}

      <div className="flex flex-col justify-center">
        <h1 className="font-bold text-xl sm:text-2xl lg:text-3xl">
          {beerInfo?.beerDetailsBasic?.korName}
        </h1>
        <div className="text-xs sm:text-sm lg:text-lg">
          <span>{beerInfo?.beerDetailsBasic?.country}</span>
          <span>
            /
            {beerInfo?.beerCategoryTypes === undefined ? (
              <></>
            ) : (
              beerInfo?.beerCategoryTypes?.map((el: string, idx: number) => {
                return (
                  <span className="mx-0.5" key={idx}>
                    {el}
                  </span>
                );
              })
            )}
          </span>
          <span>/ {beerInfo?.beerDetailsBasic?.abv}%</span>
          <span>/ {beerInfo?.beerDetailsBasic?.ibu} IBU</span>
        </div>
        <div className="my-2">
          <span className="font-semibold sm:text-xl lg:text-2xl">
            ⭐️ {beerInfo?.beerDetailsStars?.totalAverageStars}
          </span>
          <span className="text-y-gray ml-1 text-xs sm:text-sm lg:text-lg">
            ({beerInfo?.beerDetailsCounts?.ratingCount} ratings)
          </span>
        </div>
        <div>
          {beerInfo?.beerDetailsTopTags === null ? (
            <></>
          ) : (
            beerInfo?.beerDetailsTopTags?.map((el: string, idx: number) => {
              return <Tag key={idx}>{el}</Tag>;
            })
          )}
        </div>
      </div>
    </div>
  );
}
