import Link from 'next/link';
import React, { useEffect, useState } from 'react';
import PairingCard from './PairingCard';
import Image from 'next/image';

export default function PairingCardController(props: {
  pairingCardProps: any;
}) {
  const [cardPropsList, setCardPropsList] = useState<any>();
  useEffect(() => {
    if (props.pairingCardProps !== undefined)
      setCardPropsList(props.pairingCardProps.data);
  }, [props.pairingCardProps]);
  console.log('cardPropsList', cardPropsList);
  return (
    <>
      {cardPropsList?.length === 0 ? (
        <div className="noneContent py-44 border-none shadow-none">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 페어링이 없습니다.
        </div>
      ) : (
        <>
          {cardPropsList?.map((pairingCardProps: any) => (
            <Link
              href={`/pairing/${pairingCardProps.pairingId}`}
              key={pairingCardProps.pairingId}
            >
              <PairingCard pairingCardProps={pairingCardProps} />
            </Link>
          ))}
        </>
      )}
    </>
  );
}
