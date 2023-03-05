import { VscBell, VscBellDot } from 'react-icons/vsc';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import Image from 'next/image';
import axios from '@/pages/api/axios';
export default function Alarm() {
  type noti = {
    id: number;
    commentUserImage: string;
    title: string;
    content: string | undefined | null;
    notifyType: string;
    idForNotifyType: number;
    createdAt: string;
    isRead: boolean;
  };

  const [alarmList, setAlarmList] = useState<noti[] | undefined>();
  const [unreadCount, setUnreadCount] = useState<number>(0);
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState<boolean>(false);
  const [isConnect, setIsConnect] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
      // axios.get(`/subscribe`).then((data) => {
      //   console.log('data', data);
      // });
      setAlarmList([
        {
          id: 1,
          commentUserImage:
            'https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/1.png',
          title: '닉네임2님이 회원님의 게시글에 댓글을 남겼습니다.',
          content: '"와 이거 맛있어요요."',
          notifyType: 'RATING',
          idForNotifyType: 1,
          createdAt: '2023-03-01T16:07:50.385415',
          isRead: false,
        },
        {
          id: 2,
          commentUserImage:
            'https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/1.png',
          title: '닉네임4님이 회원님의 게시글에 댓글을 남겼습니다.',
          content: '"와 이거 맛있어요요."',
          notifyType: 'RATING',
          idForNotifyType: 1,
          createdAt: '2023-03-01T16:07:50.385415',
          isRead: false,
        },
        {
          id: 3,
          commentUserImage:
            'https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/1.png',
          title: '닉네임4님의 팔로잉요청입니다.',
          content: '"와 이거 맛있어요요."',
          notifyType: 'RATING',
          idForNotifyType: 1,
          createdAt: '2023-03-01T16:07:50.385415',
          isRead: false,
        },
      ]);
      setUnreadCount(1);
    }
  }, [TOKEN]);

  // const EventSource = EventSourcePolyfill || NativeEventSource;
  // EventSource 객체 속성 1.onmessage 기본 메세지 / 2.onopen 접속 / 3.onerror 오류
  // useEffect(() => {
  //   if (isLogin) {
  //     let eventSource: any;
  //     const fetchSse = async () => {
  //       try {
  // eventSource = new EventSource(`${process.env.API_URL}/api/subscribe`);
  /* EVENTSOURCE ONMESSAGE */
  // eventSource.onmessage = async (event: any) => {
  //   const res = await event.data;
  //   if (!res.includes('EventStream Created.')) setNewAlarm(true); // 헤더 마이페이지 아이콘 상태 변경
  // };

  /* EVENTSOURCE ONERROR */
  // eventSource.onerror = async (event: any) => {
  //   if (!event.error.message.includes('No activity'))
  //     eventSource.close();
  // };
  //       } catch (error) {}
  //     };
  //     fetchSse();
  //     return () => eventSource.close();
  //   }
  // });
  return (
    <div className="mr-4 mb-0.5 relative">
      <div onClick={() => setShowModal(true)}>
        {isLogin && alarmList ? (
          <div className="relative">
            <VscBellDot className=" w-[28px] h-[28px]" />
            <span className="absolute top-[1px] right-[1px] w-[11px] h-[11px] rounded-full bg-y-brown"></span>
          </div>
        ) : isLogin ? (
          <VscBell className="w-[28px] h-[28px]" />
        ) : (
          <></>
        )}
      </div>
      <div className="absolute top-8 -right-3 h-0">
        {showModal ? (
          <div>
            <ul className="bg-white border rounded-lg py-2 shadow-md">
              {alarmList && alarmList.length > 0 ? (
                <>
                  {alarmList.map((el: noti) => (
                    <li
                      key={el.id}
                      className="text-[8px] px-2 py-1 lg:text-xs truncate "
                    >
                      <Image
                        alt="userImg"
                        src={el?.commentUserImage}
                        width={10}
                        height={10}
                        className="w-4 h-4 inline mr-1"
                        priority
                      />
                      <span>{el.title}</span>
                    </li>
                  ))}
                </>
              ) : (
                <li className="text-[8px] text-y-brown px-4 py-1 lg:text-xs truncate">
                  알림이 없습니다.
                </li>
              )}
            </ul>
            <button
              className="inset-0 fixed cursor-default"
              onClick={() => setShowModal(false)}
            ></button>
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}
