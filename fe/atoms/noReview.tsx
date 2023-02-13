import { atom } from 'recoil';

export interface NoReviewTypes {
  id: number;
  contents: string;
}

export const noReview = atom<NoReviewTypes[]>({
  key: `noreview`,
  default: [
    {
      id: 0,
      contents: '설명은 생략한다',
    },

    {
      id: 1,
      contents: '리뷰를 작성하지 않았습니다.',
    },

    {
      id: 2,
      contents: '리뷰가 없어요..!',
    },
  ],
});
