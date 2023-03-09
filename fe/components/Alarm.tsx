import { FiBellOff } from 'react-icons/fi';
import { CgBell } from 'react-icons/cg';

import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import Image from 'next/image';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';

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

  const router = useRouter();
  const curRouter = router.route;
  const [alarmList, setAlarmList] = useState<noti[] | undefined>();
  const [unreadCount, setUnreadCount] = useState<number>(0);
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);

  useEffect(() => {
    /* 1. SSE 로 알림 기능 구현 */
    // EventSource 객체 속성 1.onmessage 기본 메세지 / 2.onopen 접속 / 3.onerror 오류
    // if (isLogin) {
    // const fetchSse = async () => {
    // const eventSource = new EventSource(`${process.env.API_URL}/subscribe`);
    // eventSource.addEventListener('message', function (e) {
    // console.log('!!!!!!!!!!!!!!!!data', e.data);
    // });
    // connection되면
    // eventSource.addEventListener('open', function (e) {
    // Connection was opened.
    // console.log('Connection was opened1.');
    // });
    // error 나면
    // eventSource.addEventListener('error', function (e) {
    // if (e.readyState == EventSource.CLOSED) {
    // console.log('Connection was closed0.');
    // }
    // });
    // try {
    //   let eventSource = new EventSource(`${process.env.API_URL}/subscribe`);
    //   console.log('1단계');
    //   /* EVENTSOURCE ONMESSAGE */
    //   eventSource.onmessage = async (event: any) => {
    //     // const res = await event.data;
    //     // if (!res.includes('EventStream Created.'))
    //     console.log('오 이게 되네'); // 헤더 마이페이지 아이콘 상태 변경
    //   };
    //   /* EVENTSOURCE ONERROR */
    //   eventSource.onerror = async (event: any) => {
    //     console.log('오 이게 안되네');
    //     // if (!event.error.message.includes('No activity'))
    //     eventSource.close();
    //   };
    // } catch (error) {
    //   console.log('ERROR!', error);
    // }
    // };
    // fetchSse();
    // return () => eventSource.close();
    // }
    if (isLogin) {
      initNotify();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [TOKEN, isLogin, curRouter]);

  const initNotify = () => {
    /* 2. 보통의 axios 로 알림 기능 구현 */

    axios
      .get(`/api/notifications`)
      .then((response) => {
        setAlarmList(response.data.notifications);
        setUnreadCount(response.data.unreadCount);
      })
      .catch((error) => console.log(error));
  };

  const handleNotify = (
    id: number,
    notifyType: string,
    idForNotifyType?: number
  ) => {
    if (notifyType === 'RATING') {
      const notifyid = idForNotifyType;
      axios
        .delete(`/api/notifications/${id}`)
        .then(() => {
          setShowModal(false);
          initNotify();
          router.push(`/rating/${notifyid}`);
        })
        .catch((error) => console.log(error));
    } else if (notifyType === 'PAIRING') {
      const notifyid = idForNotifyType;
      axios.delete(`/api/notifications/${id}`).then(() => {
        setShowModal(false);
        initNotify();
        router.push(`/pairing/${notifyid}`);
      });
    } else if (notifyType === 'FOLLOWING') {
      axios.delete(`/api/notifications/${id}`).then(() => {
        setShowModal(false);
        initNotify();
        router.push(`/mypage`);
      });
    }
  };

  return (
    <div className="mr-4 mb-0.5 relative">
      <div onClick={() => setShowModal(true)}>
        {isLogin && alarmList && alarmList.length > 0 ? (
          <div className="relative">
            <CgBell className=" w-[28px] h-[28px]" />
            <span className="absolute top-0.5 right-0.5 w-[9px] h-[9px] rounded-full bg-y-brown"></span>
          </div>
        ) : isLogin ? (
          <CgBell className="w-[28px] h-[28px]" />
        ) : (
          <></>
        )}
      </div>
      <div className="absolute top-8 -right-3 h-0 z-0">
        {showModal ? (
          <div>
            <ul className="bg-white border rounded-lg py-2 shadow-md">
              {alarmList && alarmList.length > 0 ? (
                <>
                  {alarmList.map((el: noti) => (
                    <li
                      onClick={() =>
                        handleNotify(el.id, el.notifyType, el.idForNotifyType)
                      }
                      key={el.id}
                      className="text-[8px] px-2 py-1 lg:text-xs truncate "
                    >
                      <Image
                        alt="userImg"
                        src={el?.commentUserImage}
                        width={10}
                        height={10}
                        className="w-4 h-4 inline mr-1 rounded-full"
                        priority
                      />
                      <span>{el.title}</span>
                    </li>
                  ))}
                </>
              ) : (
                <li className="text-[8px] text-y-gray px-4 lg:text-xs truncate">
                  <FiBellOff className="w-4 h-4 m-auto mb-1" />
                  알림이 없습니다.
                </li>
              )}
              <button
                className="inset-0 fixed cursor-default bg-[rgba(0,0,0,0.3)] -z-10"
                onClick={() => setShowModal(false)}
              ></button>
            </ul>
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}
