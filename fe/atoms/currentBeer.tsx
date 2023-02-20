import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

// sessionStorage에 저장하고 싶은 경우
// Next.js를 쓴다면 sessionStorage는 아래와 같이 따로 설정 필요
const sessionStorage =
  typeof window !== 'undefined' ? window.sessionStorage : undefined;

const { persistAtom } = recoilPersist({
  key: 'sessionAtom',
  storage: sessionStorage,
});

//Recoil-persist를 적용시키려면 아래의 effects_UNSTABLE을 적어주어야 한다.
export const currentBeer = atom<any>({
  key: 'currentBeer',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

// 참고: https://velog.io/@timosean/Web-Recoil-persist-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0
