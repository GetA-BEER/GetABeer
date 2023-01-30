import { BeerInfo } from './SmallBeerController';
import Image from 'next/image';

export default function SmallBeer(props: BeerInfo) {
  {
    console.log('props.idx', props.idx);
    //index 로 사용하는 법 알아오기..
  }

  return (
    <div className="rounded-2xl w-full m-2 bg-white text-y-black drop-shadow-xl text-[5px] border">
      <div
        className={`${props.id % 2 === 0 ? 'bg-y-cream' : 'bg-y-lemon'} p-4`}
      >
        <div className="text-base font-semibold">{props.title}</div>
        <div>{`${props.category} / ${props.country} ${props.level}% ${props.ibu}IBU`}</div>
      </div>
      <Image
        className="pt-3 rounded-2xl m-auto"
        alt="Beer"
        src={`${props.image}`}
        width={300}
        height={300}
      />
    </div>
  );
}
