import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const localStorage =
  typeof window !== 'undefined' ? window.localStorage : undefined;

const { persistAtom } = recoilPersist({
  key: 'localStorageAtom',
  storage: localStorage,
});

//Recoil-persist를 적용시키려면 아래의 effects_UNSTABLE을 적어주어야 한다.
export const searchHistory = atom<any>({
  key: 'searchHistory',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

// import { useRecoilState } from 'recoil';
// import { searchHistoryList } from '@/atoms/searchHistory';
//  const [아무거나] = useRecoilState(searchHistoryList);
