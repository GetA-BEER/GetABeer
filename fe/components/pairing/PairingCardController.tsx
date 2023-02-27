import React, { useEffect, useState } from 'react';
import PairingCard from './PairingCard';
import ProfileCard from './ProfileCard';
import Image from 'next/image';
import { CategoryMatcherToKor } from '@/utils/CategryMatcher';
import { TimeHandler } from '@/utils/TimeHandler';

export default function PairingCardController(props: {
  pairingCardProps: any;
}) {
  const [cardPropsList, setCardPropsList] = useState<any>();
  const [date, setDate] = useState<any>('');
  const initialDate = props?.pairingCardProps?.createdAt;

  useEffect(() => {
    if (props.pairingCardProps !== undefined) {
      setCardPropsList(props.pairingCardProps);
    }
  }, [props]);

  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

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
          {cardPropsList?.map((el: any, idx: number) => (
            <div
              key={idx}
              className="rounded-lg bg-white text-y-black text-xs border-2 mx-2 mt-3 relative"
            >
              {/*닉네임, 날짜*/}
              <ProfileCard nickname={el?.nickname} date={date} />
              <span className="top-3 right-3 px-2 py-[2px] text-[8px] rounded-md bg-y-gold text-white absolute">
                {CategoryMatcherToKor(el?.category)}
              </span>
              <PairingCard pairingCardProps={el} />
            </div>
          ))}
        </>
      )}
    </>
  );
}
