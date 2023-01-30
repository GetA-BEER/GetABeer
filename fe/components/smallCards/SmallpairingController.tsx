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

export default function SmallCardController({ pairingProps }: any) {
  const [smallPairingList, setSmallPairingList] =
    useState<SmallPairingCardInfo[]>(pairingProps);

  return (
    <div className="grid grid-cols-2 gap-3 px-3">
      {smallPairingList?.map((props: SmallPairingCardInfo) => (
        <SmallPairingCard {...props} key={props.id} />
      ))}
    </div>
  );
}
