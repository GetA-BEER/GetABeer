import SmallPairingCard from './SmallPairingCard';
import React, { useState } from 'react';

export interface SmallPairingCardInfo {
  id: number;
  pairing: string;
  nickName: string;
  description: string;
  date: string;
  comments: number;
  thumbs: number;
}

export default function SmallCardController() {
  const [smallPairingList, setSmallPairingList] = useState<
    SmallPairingCardInfo[]
  >([
    {
      id: 1,
      pairing: '튀김',
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 2,
      pairing: '구이',
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 3,
      pairing: '견과류',
      nickName: '어렵네',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 4,
      pairing: '과자',
      nickName: '유진님',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 5,
      pairing: '튀김',
      nickName: '테스트',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
    {
      id: 6,
      pairing: '튀김',
      nickName: '어렵네',
      description:
        '펠롱은 반짝이라는 의미의 제주 사투리 입니다.펠롱은 반짝이라는 의미제주 사투리 입니다,펠롱은 반짝이라는 의미의 제주 사투리 입니다,펠롱은반짝이라는 의미의 제주 사투리 입니다',
      date: '2023.41.30',
      comments: 5,
      thumbs: 10,
    },
  ]);

  return (
    <div className="grid grid-cols-2 gap-3 px-3">
      {smallPairingList?.map((props: SmallPairingCardInfo) => (
        <SmallPairingCard {...props} key={props.id} />
      ))}
    </div>
  );
}
