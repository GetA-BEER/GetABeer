import SmallCard from './SmallCard';
// import SmallCardInfo from '@/pages/main';
import React, { useState } from 'react';

export interface SmallCardInfo {
  id: number;
  star: number;
  nickName: string;
  description: string;
  date: string;
  comments: number;
  thumbs: number;
}

export default function SmallCardController({ cardProps }: any) {
  const [cardPropsList, setCardPropsList] =
    useState<SmallCardInfo[]>(cardProps);
  return (
    <div className="grid grid-cols-2 gap-3 px-3">
      {cardPropsList?.map((props: SmallCardInfo) => (
        <SmallCard {...props} key={props.id} />
      ))}
    </div>
  );
}
