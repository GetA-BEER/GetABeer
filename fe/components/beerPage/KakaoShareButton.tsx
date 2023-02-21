import { useEffect } from 'react';
import { HiShare } from 'react-icons/hi';
import { RiKakaoTalkFill } from 'react-icons/ri';

import { useRecoilValue, useRecoilState } from 'recoil';

// 참고로, JS SDK는 PC 또는 모바일에 따라 동작이 변경되는 부분들이 있어서
// user agent가 임의로 변경된 환경 (크롬 브라우저 > 개발자모드 > 모바일 설정)을 지원하지 않음
export default function KakaoShareButton() {
  useEffect(() => {
    createKakaoButton();
  }, []);

  const createKakaoButton = () => {
    if (window.Kakao) {
      const kakao = window.Kakao;
      // 중복 initialization 방지
      if (!kakao.isInitialized()) {
        // 내 javascript key 를 이용하여 initialize
        kakao.init(process.env.NEXT_PUBLIC_KAKAO_API_KEY);
      }

      kakao.Link.createDefaultButton({
        // Render 부분 id=kakao-link-btn 을 찾아 그부분에 렌더링을 합니다
        container: '#kakao-link-btn',
        objectType: 'feed',
        content: {
          title: 'GetABeer',
          description: `#맥주가 #땡길땐 #GetABeer`,
          imageUrl:
            'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
          link: {
            mobileWebUrl: window.location.href,
            webUrl: window.location.href,
          },
        },
        buttons: [
          {
            title: '웹으로 보기',
            link: {
              mobileWebUrl: window.location.href,
              webUrl: window.location.href,
            },
          },
          {
            title: '앱으로 보기',
            link: {
              mobileWebUrl: window.location.href,
              webUrl: window.location.href,
            },
          },
        ],
      });
    }
  };

  return (
    <button
      type="button"
      id="kakao-link-btn"
      className="hover:text-y-gold mr-1"
    >
      <span className="mr-1">
        <HiShare className="inline" /> 공유하기
      </span>
    </button>
  );
}
// export default KakaoShareButton;
