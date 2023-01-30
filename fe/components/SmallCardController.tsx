import SmallCard from './SmallCard';
import SmallCardInfo from '@/pages/main';
import React, { useState } from 'react';

interface SmallCardInfo {
  id: number;
  star: number;
  nickName: string;
  description: string;
  date: string;
  comments: number;
  thumbs: number;
}

export default function SmallCardController() {
  const [smallCardList, setSmallCardList] = useState<SmallCardInfo[]>([
    {
      id: 1,
      star: 4.0,
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 2,
      star: 4.0,
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 3,
      star: 4.0,
      nickName: '어렵네',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 4,
      star: 4.0,
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 5,
      star: 4.0,
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 6,
      star: 4.0,
      nickName: '어렵네',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
  ]);

  return (
    <div className="grid grid-cols-2 gap-5">
      {smallCardList?.map((props: SmallCardInfo) => (
        <SmallCard {...props} key={props.id} />
      ))}
    </div>
  );
}
