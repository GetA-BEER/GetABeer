import React, { useState } from 'react';
import PairingCard from './PairingCard';

export interface PairingCardInfo {
  id: number;
  title: string;
  nickname: string;
  date: string;
  comment: number;
  thumb: number;
  image?: string;
  description?: string;
}

export default function PairingCardController(props: {
  pairingCardProps: PairingCardInfo[];
}) {
  const [cardPropsList, setCardPropsList] = useState<PairingCardInfo[]>(
    props.pairingCardProps
  );
  return (
    <>
      {cardPropsList?.map((pairingCardProps: PairingCardInfo, idx: number) => (
        <PairingCard
          pairingCardProps={pairingCardProps}
          idx={idx}
          key={pairingCardProps.id.toString()}
        />
      ))}
    </>
  );
}
