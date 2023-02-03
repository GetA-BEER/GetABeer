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
    <div className="border">
      {cardPropsList?.map((pairingCardProps: PairingCardInfo) => (
        <PairingCard
          pairingCardProps={pairingCardProps}
          key={pairingCardProps.id.toString()}
        />
      ))}
    </div>
  );
}
