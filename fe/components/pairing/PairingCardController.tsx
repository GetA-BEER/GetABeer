import React, { useEffect, useState } from 'react';
import PairingCard from './PairingCard';
import ProfileCard from './ProfileCard';
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
        <></>
      ) : (
        <>
          {cardPropsList?.map((el: any, idx: number) => (
            <div
              key={idx}
              className="rounded-lg bg-white text-y-black text-xs border-2 mx-2 mt-3 relative"
            >
              {/*닉네임, 날짜*/}
              <ProfileCard
                nickname={el?.nickname}
                date={date}
                userImage={el?.userImage}
              />
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
