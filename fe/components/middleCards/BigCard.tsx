import Tag from '../Tag';
import Image from 'next/image';
import { BsChatDots } from 'react-icons/bs';
import { BiUser } from 'react-icons/bi';

export default function BigCard({ cardProps }: any) {
  return (
    <div className="flex flex-col rounded-lg bg-white text-y-black border border-y-lightGray m-2">
      <div className="flex">
        <div className="relative w-[120px] h-[150px]">
          <Image
            alt={cardProps?.korName}
            src={cardProps?.thumbnail}
            fill
            className="object-none object-top "
          />
        </div>
        <div className="py-4">
          <h1 className="font-bold text-2xl sm:text-3xl lg:text-4xl">
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
            {cardProps?.beerTags.map((el: string, idx: number) => {
              return <Tag key={idx}>{el}</Tag>;
            })}
          </div>
        </div>
      </div>
      <div>
        <div className="flex border-t border-y-lightGray">
          <div className="mt-4">
            <BsChatDots className="ml-4" />
          </div>
          <div className="flex flex-col text-sm my-4 mx-2">
            <div className="text-xs sm:text-sm lg:text-lg">
              <span>⭐️ 5.0 </span>
              <span>
                제주도 유기농 감귤 껍질을 첨가하여 상큼하고 부드러운 맛이 나는
                밀맥주
              </span>
            </div>
            <div className="flex justify-end mt-1 mr-2 text-xs sm:text-sm lg:text-lg">
              <BiUser className=" bg-y-brown text-white rounded-full w-5 h-5 mr-1" />
              <span>serin-B</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
