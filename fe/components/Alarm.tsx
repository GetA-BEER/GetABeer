import { VscBell, VscBellDot } from 'react-icons/vsc';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { accessToken } from '@/atoms/login';
import axios from 'axios';

export default function Alarm() {
  const [newAlarm, setNewAlarm] = useState(false);
  const [alarmList, setAlarmList] = useState(false);
  const TOKEN = useRecoilValue(accessToken);
  const [isLogin, setIsLogin] = useState(false);
  const [isConnect, setIsConnect] = useState(false);

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
      axios.get(`https://server.getabeer.co.kr/api/subscribe`).then((data) => {
        console.log('data', data);
      });
    }
  }, [TOKEN]);

  // const EventSource = EventSourcePolyfill || NativeEventSource;
  // EventSource 객체 속성 1.onmessage 기본 메세지 / 2.onopen 접속 / 3.onerror 오류
  useEffect(() => {
    if (isLogin) {
      let eventSource: any;
      const fetchSse = async () => {
        try {
          eventSource = new EventSource(`${process.env.API_URL}/api/subscribe`);
          console.log(eventSource);
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
        } catch (error) {}
      };
      fetchSse();
      return () => eventSource.close();
    }
  });
  return (
    <>{isLogin && newAlarm ? <VscBellDot /> : isLogin ? <VscBell /> : <></>}</>
  );
}
