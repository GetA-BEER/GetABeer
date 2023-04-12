import { ChatBalloonLeft, ChatBalloonRight } from './ChatBalloon';
import CommentInput from './inputs/CommentInput';
import { useRecoilValue } from 'recoil';
import { userId, userNickname } from '@/atoms/login';
import { useState, useEffect, useRef } from 'react';
import { ChatProps } from './Chat';
import { client } from './Chat';
import axios from '@/pages/api/axios';

export default function ChatRoom({ roomId }: { roomId: number }) {
  const [inputState, setInputState] = useState('');
  const [chatList, setChatList] = useState<ChatProps[]>([]);
  const [open, setOpen] = useState<boolean>(true);

  const USERID = useRecoilValue(userId);
  const USERNAME = useRecoilValue(userNickname);

  useEffect(() => {
    axios.get(`/api/chats/message/${roomId}`).then((res) => {
      setChatList(res.data);
    });
  }, [roomId]);

  const PubMessageObj = (msg: string) => {
    client.publish({
      destination: `/pub/api/chats/${roomId}`,
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
          userRole: 'ROLE_ADMIN',
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
    <div className="">
      <ul className="flex flex-col gap-y-2 border shadow-sm mb-1 p-1 rounded-lg w-full h-full ">
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
  );
}
