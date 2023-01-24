import { atom } from 'recoil';

export interface ITodoTypes {
  id: number;
  contents: string;
  isBoolean: boolean;
}

// string 값을 atom으로 관리하는 방식
export const stringState = atom<string>({
  key: 'name',
  // key의 값은 항상 고유값이어야 합니다.
  default: '',
});

// 업데이트 시킬 atom 배열
export const arrayState = atom<ITodoTypes[]>({
  key: 'array',
  // default에는 임의의 데이터를 넣어줍시다.
  default: [
    {
      id: 1,
      contents: 'List를',
      isBoolean: false,
    },

    {
      id: 2,
      contents: '자유롭게',
      isBoolean: false,
    },

    {
      id: 3,
      contents: '추가해보세요!',
      isBoolean: false,
    },
  ],
});
