import { useState, useEffect, useRef, ReactNode } from 'react';
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
  const bottomRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (chatList.length <= 10) {
      bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
    } else {
      bottomRef.current?.scrollIntoView({ behavior: 'auto' });
    }
  }, [open, chatList]);

  // if (typeof window !== 'undefined') {
  //   const socket = new WebSocket('wss://f3ff-175-210-242-219.jp.ngrok.io/ws/');
  //   console.log(socket, socket.readyState);
  //   socket.addEventListener('open', () => {
  //     console.log('드디어 서버와 연결되었다! ✅');
  //   });
  //   socket.addEventListener('message', (message) => {
  //     console.log('서버로부터 온 메세지: ', message);
  //   });
  //   socket.onopen = () => {
  //     socket.send('클라이언트에서 날리는 메시지');
  //   };
  // }

  const inputValid = (str: string) => {
    const strTrim = str.trim();
    if (str === (`\n` || '')) {
      return false;
    } else if (strTrim === '') {
      return false;
    } else {
      return true;
    }
  };
  const postChat = () => {
    if (inputValid(inputState)) {
      setChatList([
        ...chatList,
        {
          time: new Date().getTime(),
          role: 'user',
          msg: inputState,
        },
      ]);
    }
    setInputState('');
  };
  return (
    <div>
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
          <h3 className="flex items-start text-y-gray text-xs font-thin my-0.5">
            응답시간: 평일 14:00~18:00 (주말/공휴일 휴무)
          </h3>
          <ul className="flex flex-col gap-y-2 border shadow-sm mb-1 p-1 rounded-lg w-full h-full overflow-scroll">
            <ChatBalloonLeft>
              안녕하세요. Get A Beer 운영자입니다.
              <br />
              <br />
              문의주시는 내용은 내부 검토 후 <br />
              빠르게 답변 및 반영하겠습니다.
              <br />
              <br />
              +새로운 맥주가 추가되길 원하시면, <br />
              아래 버튼을 누르고 맥주의 이름을 <br />
              정확하게 입력하여 보내주세요.
              <br />
              <button
                className="bg-white text-y-brown rounded-lg px-12 mb-1"
                onClick={() => {
                  setInputState('신청하는 맥주 이름: ');
                }}
              >
                맥주 등록신청
              </button>
            </ChatBalloonLeft>
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
            <div ref={bottomRef} />
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
