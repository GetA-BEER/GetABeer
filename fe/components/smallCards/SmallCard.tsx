import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { SmallCardInfo } from './SmallCardController';
import Image from 'next/image';

export default function SmallCard(props: SmallCardInfo) {
  return (
    <div className="w-full rounded-lg bg-white text-y-black drop-shadow-xl text-xs">
      {/* 별점,닉네임 */}
      <div className="flex justify-between py-1 px-2">
        <span className="flex justify-center items-center">
          <Image
            src="/images/star.png"
            alt="star"
            width={20}
            height={20}
            className="mr-1 mb-[3px] text-y-gold drop-shadow-md"
          />
          {props.star}
        </span>
        <span className="flex justify-center items-center">
          {props.nickName}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full w-4 h-4" />
        </span>
      </div>
      {/* 설명 */}
      <p className="p-2 h-28 overflow-hidden w-full border-y-2 border-gray-200 leading-6">
        {props.description}.....<span className="text-y-gold">더보기</span>
      </p>
      {/* 날짜,코멘트수,엄지수 */}
      <div className="p-2 flex justify-between items-center">
        <div className="text-y-gray">{props.date}</div>
        <div className="flex">
          <span className="flex justify-center items-center ">
            <FaRegCommentDots className="mr-[2px]" />
            {props.comments}
          </span>
          <span className="ml-1 flex justify-center items-center">
            <FiThumbsUp />
            {props.thumbs}
          </span>
        </div>
      </div>
    </div>
  );
}
