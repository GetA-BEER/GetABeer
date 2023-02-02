import WishCard from './WishCard';
import React, { useState } from 'react';

export interface WishInfo {
  id: number;
  title: string;
  category: string;
  country: string;
  level: number;
  ibu: number;
  heart: number;
  image: string;
}

export default function WishCardController(props: { wishProps: WishInfo[] }) {
  const [wishList, setWishList] = useState<WishInfo[]>(props.wishProps);
  return (
    <div className="grid grid-cols-2 gap-3">
      {wishList?.map((wishProps: WishInfo, idx: number) => (
        <WishCard key={idx.toString()} wishProps={wishProps} idx={idx + 1} />
      ))}
    </div>
  );
}
