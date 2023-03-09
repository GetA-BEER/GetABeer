import { useState } from 'react';
import { BsChatDotsFill, BsPersonCircle } from 'react-icons/bs';
import { IoClose } from 'react-icons/io5';
import { ChatBalloonLeft, ChatBalloonRight } from './ChatBalloon';
import CommentInput from './inputs/CommentInput';

interface ChatProps {
  time: number;
  role: 'user' | 'mater';
  msg: string;
}

export default function Chat() {
  const [open, setOpen] = useState(false);
  const [inputState, setInputState] = useState('');
  const [chatList, setChatList] = useState<ChatProps[]>([]);

  if (typeof window !== 'undefined') {
    const socket = new WebSocket('wss://f3ff-175-210-242-219.jp.ngrok.io/ws/');
    console.log(socket, socket.readyState);
    socket.addEventListener('open', () => {
      console.log('드디어 서버와 연결되었다! ✅');
    });
    socket.addEventListener('message', (message) => {
      console.log('서버로부터 온 메세지: ', message);
    });
    socket.onopen = () => {
      socket.send('빈님 가나요...??');
    };
  }

  const postChat = () => {
    if (inputState !== '') {
      chatList.push({
        time: new Date().getTime(),
        role: 'user',
        msg: inputState,
      });
    }
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
          <ul className="flex flex-col gap-y-2 border shadow-sm mb-1 py-1 rounded-lg w-full h-full overflow-scroll">
            <ChatBalloonLeft>운영자가 말하는 말풍선</ChatBalloonLeft>
            <ChatBalloonRight>유저가 말하는 말풍선</ChatBalloonRight>
            {chatList.map((el) => {
              return (
                <li key={el.time}>
                  {el.role === 'user' ? (
                    <ChatBalloonRight>{el.msg}</ChatBalloonRight>
                  ) : (
                    <ChatBalloonLeft>{el.msg}</ChatBalloonLeft>
                  )}
                </li>
              );
            })}
          </ul>
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
