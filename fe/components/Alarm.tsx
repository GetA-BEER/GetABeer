import { FiBellOff } from 'react-icons/fi';
import { CgBell } from 'react-icons/cg';
import { GiConfirmed } from 'react-icons/gi';
import { useEffect, useRef, useState } from 'react';
import { useRecoilState } from 'recoil';
import { accessToken } from '@/atoms/login';
import Image from 'next/image';
import axios from '@/pages/api/axios';
import { useRouter } from 'next/router';
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
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
  const eventSource = useRef<EventSourcePolyfill | EventSource>();
  const router = useRouter();
  const curRouter = router.route;
  const [alarmList, setAlarmList] = useState<noti[] | undefined>();
  const [unreadCount, setUnreadCount] = useState<number>(0);
  const [TOKEN] = useRecoilState(accessToken);
  const [isLogin, setIsLogin] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);

  useEffect(() => {
    if (TOKEN === '') {
      setIsLogin(false);
    } else {
      setIsLogin(true);
      initNotify();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [TOKEN]);
  useEffect(() => {
    if (TOKEN !== '') {
      const EventSource = EventSourcePolyfill || NativeEventSource;
      /* 1. SSE 로 알림 기능 구현 */
      if (isLogin) {
        eventSource.current = new EventSource(
          `http://localhost:8080/subscribe`,
          {
            headers: {
              Authorization: TOKEN,
            },
            heartbeatTimeout: 600000,
            withCredentials: true,
          }
        );
        eventSource.current.onmessage = (event: any) => {
          if (event.data[0] === 'E') console.log('요기지룡!', event.data);
          // console.log(event.data[0]);
          console.log('요기지룡!', event.data);
          // setAlarmList(event.data.notifications);
          // setUnreadCount(event.data.unreadCount);
        };
        eventSource.current.onopen = (event: any) => {
          console.log('open ㅎㅎ', event);
        };
        eventSource.current.onerror = (event: any) => {
          // if (event.data[0] === 'E')
          console.log('에러 ㅎㅎ');
        };
      }
      return () => eventSource.current?.close();
    }
  }, [TOKEN, isLogin]);

  const initNotify = () => {
    /* 2. 보통의 axios 로 알림 기능 구현 */
    if (TOKEN !== '') {
      const config = {
        headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
        withCredentials: true,
      };
      axios
        .get(`/api/notifications`, config)
        .then((response) => {
          setAlarmList(response.data.notifications);
          setUnreadCount(response.data.unreadCount);
        })
        .catch((error) => console.log(error));
    }
  };

  const handleNotify = (
    id: number,
    notifyType: string,
    idForNotifyType?: number
  ) => {
    if (notifyType === 'RATING' && TOKEN !== '') {
      const notifyid = idForNotifyType;
      const config = {
        headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
        withCredentials: true,
      };
      axios
        .delete(`/api/notifications/${id}`, config)
        .then(() => {
          setShowModal(false);
          initNotify();
          router.push(`/rating/${notifyid}`);
        })
        .catch((error) => console.log(error));
    } else if (notifyType === 'PAIRING' && TOKEN !== '') {
      const notifyid = idForNotifyType;
      const config = {
        headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
        withCredentials: true,
      };
      axios.delete(`/api/notifications/${id}`, config).then(() => {
        setShowModal(false);
        initNotify();
        router.push(`/pairing/${notifyid}`);
      });
    } else if (notifyType === 'FOLLOWING' && TOKEN != '') {
      const notifyid = idForNotifyType;
      const config = {
        headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
        withCredentials: true,
      };
      axios.delete(`/api/notifications/${id}`, config).then(() => {
        setShowModal(false);
        initNotify();
        // userpage로 이동 필요
        router.push(`/userpage/${notifyid}`);
      });
    }
  };

  const handleAllDelete = () => {
    const config = {
      headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
      withCredentials: true,
    };
    axios
      .delete(`/api/notifications`, config)
      .then(() => {
        setShowModal(false);
        initNotify();
      })
      .catch((error) => console.log(error));
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
                  <li
                    onClick={handleAllDelete}
                    className="text-[8px] px-4 py-1 lg:text-xs text-end text-y-gray hover:text-y-gold"
                  >
                    모두 읽음으로 표시
                    <GiConfirmed className="inline mb-0.5 ml-1" />
                  </li>
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
