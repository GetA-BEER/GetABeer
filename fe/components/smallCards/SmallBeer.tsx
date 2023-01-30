import { BeerInfo } from './SmallBeerController';
import Image from 'next/image';

export default function SmallBeer(props: BeerInfo, idx: number) {
  console.log({ idx });
  return (
    <div className="rounded-2xl w-5/12 m-2 bg-white text-y-black drop-shadow-xl text-xs border">
      <div className={`${idx % 2 === 1 ? 'bg-y-cream' : 'bg-y-lemon'} p-4`}>
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
