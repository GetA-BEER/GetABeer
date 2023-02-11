import SmallCard from './SmallCard';
import React, { useState } from 'react';

export interface SmallCardInfo {
  id: number;
  star: number;
  nickName: string;
  description?: string;
  date: string;
  comments: number;
  thumbs: number;
  tags: string[];
}

export default function SmallCardController(props: {
  cardProps: SmallCardInfo[];
}) {
  const [cardPropsList, setCardPropsList] = useState<SmallCardInfo[]>(
    props.cardProps
  );
  return (
    <div className="grid grid-cols-2 gap-3 px-3">
      {cardPropsList?.map((cardProps: SmallCardInfo) => (
        <SmallCard cardProps={cardProps} key={cardProps.id.toString()} />
      ))}
    </div>
  );
}
