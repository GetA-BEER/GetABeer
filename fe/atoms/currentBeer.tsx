import { atom } from 'recoil';

export const currentBeer = atom<any>({
  key: `currentBeer`,
  default: '',
});
