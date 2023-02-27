import { atom } from 'recoil';
import { SearchCardProps } from '@/components/middleCards/SearchCard';

export const searchingImage = atom<SearchCardProps[]>({
  key: 'searchingImage',
  default: [],
});
