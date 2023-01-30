import SmallBeer from './SmallBeer';
import React, { useState } from 'react';

export interface BeerInfo {
  id: number;
  title: string;
  category: string;
  country: string;
  level: number;
  ibu: number;
  image: string;
}

export default function SmallBeerController({ beerProps }: any) {
  const [beerList, setBeerList] = useState<BeerInfo[]>(beerProps);
  return (
    <>
      <div className="m-4 text-base font-semibold">
        인기 많은 <span className="text-y-brown">맥주</span>
      </div>
      <div className="overflow-x-scroll flex flex-1">
        {beerList?.map((props: BeerInfo, idx: number) => (
          <>
            {/* {idx} */}
            <SmallBeer {...props} key={props.id} />
          </>
        ))}
      </div>
    </>
  );
}
