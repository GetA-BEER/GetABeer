import { AiFillStar } from 'react-icons/ai';
import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { SmallCardInfo } from '@/pages/main';

export default function SmallCard(props: SmallCardInfo) {
  return (
    <div className="w-full rounded-md bg-white text-y-black drop-shadow-lg ">
      {/* 별점,닉네임 */}
      <div className="flex justify-between py-1 px-2 text-base font-semibold">
        <span className="flex justify-center items-center">
          <AiFillStar className="mr-1 text-y-yellow text-xl" />
          {props.star}
        </span>
        <span className="flex justify-center items-center">
          {props.nickName}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full text-xl" />
        </span>
      </div>
      {/* 설명 */}
      <div className="p-2 h-28 overflow-hidden w-full border-y-2 border-gray-200 text-sm">
        {props.description}...더보기
      </div>
      {/* 날짜,코멘트수,엄지수 */}
      <div className="p-2 flex justify-between items-center text-xs text-y-gray">
        <div>{props.date}</div>
        <div className="flex">
          <span className="flex justify-center items-center ">
            <FaRegCommentDots />
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
