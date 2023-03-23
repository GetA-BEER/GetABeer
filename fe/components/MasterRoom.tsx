import { useRecoilValue } from 'recoil';
import { userId } from '@/atoms/login';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';
import ChatRoom from './ChatRoom';

interface RoomlistProps {
  roomId: number;
  senderId: number;
  adminRead: boolean;
}

export default function MasterRoom() {
  const USERID = useRecoilValue(userId);
  const [roomlist, setRoomlist] = useState<RoomlistProps[]>();
  const [curChatRoom, setCurChatRoom] = useState<number>();
  useEffect(() => {
    if (USERID < 3) {
      axios.get(`/api/chats/rooms`).then((res) => setRoomlist(res.data));
    }
  }, [USERID]);

  return (
    <>
      <ul className="w-full h-full overflow-scroll">
        {roomlist?.map((el) => {
          return (
            <>
              <li
                key={el.roomId}
                className={`hover:bg-y-lightGray ${
                  el.adminRead === false ? 'text-red-700' : ''
                }`}
                onClick={() => {
                  setCurChatRoom(el.roomId);
                }}
              >
                {el.roomId}
              </li>
              {curChatRoom === el.roomId ? (
                <ChatRoom roomId={el.roomId} />
              ) : null}
            </>
          );
        })}
      </ul>
    </>
  );
}
