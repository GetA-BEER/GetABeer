import Link from 'next/link';
import React, { useState } from 'react';
import PairingCard from './PairingCard';

// export interface PairingCardInfo {
//   id: number;
//   title: string;
//   nickname: string;
//   date: string;
//   comment: number;
//   thumb: number;
//   image?: string;
//   description?: string;
// }

export default function PairingCardController(props: {
  pairingCardProps: any;
}) {
  const [cardPropsList, setCardPropsList] = useState<any>(
    props.pairingCardProps
  );
  return (
    <>
      {cardPropsList.map((pairingCardProps: any) => (
        <Link
          href={`/pairing/${pairingCardProps.pairingId}`}
          key={pairingCardProps.pairingId}
        >
          <PairingCard pairingCardProps={pairingCardProps} />
        </Link>
      ))}
    </>
  );
}
