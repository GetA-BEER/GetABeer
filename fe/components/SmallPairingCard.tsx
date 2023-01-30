import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import { SmallPairingCardInfo } from './SmallpairingController';

export default function SmallPairingCard(props: SmallPairingCardInfo) {
  return (
    <div className="w-full rounded-lg bg-white text-y-black drop-shadow-xl text-xs">
      {/* 페어링,닉네임 */}
      <div className="flex justify-between py-1 px-2">
        <span className="flex justify-center items-center px-3 py-[2px] rounded-md bg-y-gold text-white">
          {props.pairing}
        </span>
        <span className="flex justify-center items-center">
          {props.nickName}
          <BiUser className="ml-1 bg-y-brown text-white rounded-full" />
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
