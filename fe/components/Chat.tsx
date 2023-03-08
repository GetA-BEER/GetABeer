import { useState } from 'react';
import { BsChatDotsFill, BsPersonCircle } from 'react-icons/bs';
import { IoClose } from 'react-icons/io5';
import { ChatBalloonLeft, ChatBalloonRight } from './ChatBalloon';
import CommentInput from './inputs/CommentInput';

export default function Chat() {
  const [open, setOpen] = useState(false);
  const [inputState, setInputState] = useState('');
  const postChat = () => {
    console.log('운영자에게 보냅니다', inputState);
    setInputState('');
  };
  return (
    <div className=" bottom-[64px] right-3 z-[2]">
      {open ? (
        <div className="flex flex-col w-[280px] h-[350px] md:w-[400px] md:h-[480px] p-2 rounded-2xl border border-y-lightGray bg-white shadow-lg shadow-y-gray">
          <h1 className="flex justify-between items-center">
            <span className="flex items-center text-y-brown">
              <BsPersonCircle className="mr-1" /> 운영자
            </span>
            <button
              onClick={() => {
                setOpen(false);
              }}
            >
              <IoClose className="w-6 h-6" />
            </button>
          </h1>
          <h3 className="text-y-gray text-xs font-thin my-0.5">
            응답시간: 평일 14:00~18:00 (주말/공휴일 휴무)
          </h3>
          <div className="flex flex-col gap-y-2 border shadow-sm mb-1 py-1 rounded-lg w-full h-full overflow-scroll">
            <ChatBalloonLeft>운영자가 말하는 말풍선</ChatBalloonLeft>
            <ChatBalloonRight>유저가 말하는 말풍선</ChatBalloonRight>
          </div>
          <div>
            <CommentInput
              inputState={inputState}
              setInputState={setInputState}
              postFunc={postChat}
              placeholder="문의사항을 남겨주세요 :)"
            />
          </div>
        </div>
      ) : (
        <button
          className="w-10 h-10 flex justify-center items-center rounded-full bg-y-brown shadow-lg shadow-y-gray"
          onClick={() => {
            setOpen(true);
          }}
        >
          <BsChatDotsFill className="text-white text-xl" />
        </button>
      )}
    </div>
  );
}
