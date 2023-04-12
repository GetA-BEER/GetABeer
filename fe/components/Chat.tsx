import { useState, useEffect, useRef } from 'react';
import { BsChatDotsFill, BsPersonCircle } from 'react-icons/bs';
import { IoClose } from 'react-icons/io5';
import { ChatBalloonLeft, ChatBalloonRight } from './ChatBalloon';
import CommentInput from './inputs/CommentInput';
import { useRecoilValue } from 'recoil';
import { userId, userNickname } from '@/atoms/login';
import { Client } from '@stomp/stompjs';
import axios from '@/pages/api/axios';
import MasterRoom from './MasterRoom';

export interface ChatProps {
  roomId: number;
  messageId: number;
  userId: number;
  userNickname: string;
  userRole: 'ROLE_USER' | 'ROLE_ADMIN';
  createdAt: string;
  content: string;
  type: string;
}

export const client = new Client({
  brokerURL: process.env.NEXT_PUBLIC_WS_URL,
  connectHeaders: {
    login: 'user',
    passcode: 'password',
  },
  debug: function (str) {
    // console.log('step', str);
  },
  reconnectDelay: 10000, //자동 재 연결
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
});

export const reportChat = (USERID: number, msg: string) => {
  client.publish({
    destination: `/pub/api/chats/${USERID}`,
    body: JSON.stringify({
      id: USERID,
      content: msg,
      type: 'SUGGEST',
    }),
    skipContentLengthHeader: true,
  });
};

export default function Chat() {
  const [open, setOpen] = useState(false);
  const [inputState, setInputState] = useState('');
  const [chatList, setChatList] = useState<ChatProps[]>([]);
  const USERID = useRecoilValue(userId);
  const USERNAME = useRecoilValue(userNickname);
  const bottomRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (chatList.length <= 10) {
      bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
    } else {
      bottomRef.current?.scrollIntoView({ behavior: 'auto' });
    }
  }, [open, chatList]);

  useEffect(() => {
    if (USERID > 2) {
      axios.get(`/api/chats/message/${USERID}`).then((res) => {
        setChatList(res.data);
      });
    }
  }, [USERID]);

  client.onConnect = function (frame) {
    // console.log('서버와 연결되었다! ✅');
  };

  client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate();

  const PubMessageObj = (msg: string) => {
    client.publish({
      destination: `/pub/api/chats/${USERID}`,
      body: JSON.stringify({
        id: USERID,
        content: msg,
        type: 'SUGGEST',
      }),
      skipContentLengthHeader: true,
    });
  };

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
      PubMessageObj(inputState);
      setChatList([
        ...chatList,
        {
          roomId: USERID,
          messageId: new Date().getTime(),
          userId: USERID,
          userRole: 'ROLE_USER',
          userNickname: USERNAME,
          createdAt: '',
          content: inputState,
          type: 'SUGGEST',
        },
      ]);
    }
    setInputState('');
  };
  return (
    <>
      {USERID !== 1 ? (
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
                    <li key={el.messageId}>
                      {el.userRole === 'ROLE_USER' ? (
                        <ChatBalloonRight>{el.content}</ChatBalloonRight>
                      ) : (
                        <ChatBalloonLeft>{el.content}</ChatBalloonLeft>
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
      ) : (
        <div>
          {open ? (
            <div className="flex flex-col w-[280px] h-[350px] md:w-[400px] md:h-[480px] p-2 rounded-2xl border border-y-lightGray bg-white shadow-lg shadow-y-gray">
              <h1 className="flex justify-between items-center">
                <span className="flex items-center text-y-brown">
                  <BsPersonCircle className="mr-1" /> 채팅방
                </span>
                <button
                  onClick={() => {
                    setOpen(false);
                  }}
                >
                  <IoClose className="w-6 h-6" />
                </button>
              </h1>
              <MasterRoom />
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
      )}
    </>
  );
}
