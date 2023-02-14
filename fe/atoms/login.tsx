import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const accessToken = atom({
  key: `accessToken`,
  default: '',
  effects_UNSTABLE: [persistAtom],
});
