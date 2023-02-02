import { WishInfo } from './WishCardController';
import Image from 'next/image';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { useState } from 'react';

export default function WishCard(props: { wishProps: WishInfo; idx: number }) {
  const [heart, setHeart] = useState(props.wishProps.heart);
  const onHeart = () => {
    if (heart === 0) setHeart(1);
    else setHeart(0);
  };
  console.log('idx', props.idx);

  return (
    <div className="rounded-2xl max-w-4xl bg-white text-y-black drop-shadow-xl text-[5px] border">
      <div
        className={`${
          props.idx % 4 === 1 || props.idx % 4 === 0
            ? 'bg-y-cream'
            : 'bg-y-lemon'
        } p-4 rounded-t-2xl`}
      >
        <div className="flex justify-between">
          <div className="text-base font-semibold">{props.wishProps.title}</div>
          <div onClick={onHeart}>
            {heart === 1 ? (
              <AiFillHeart className="w-7 h-7 text-y-brown" />
            ) : (
              <AiOutlineHeart className="w-7 h-7" />
            )}
          </div>
        </div>
        <div className="-mt-2">{`${props.wishProps.category} / ${props.wishProps.country} ${props.wishProps.level}% ${props.wishProps.ibu}IBU`}</div>
      </div>
      <Image
        className="pt-3 rounded-2xl m-auto"
        alt="Beer"
        src={`${props.wishProps.image}`}
        width={300}
        height={200}
      />
    </div>
  );
}
