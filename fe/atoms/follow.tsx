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
export const following = atom<any>({
  key: 'following',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
export const follower = atom<any>({
  key: 'follower',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
