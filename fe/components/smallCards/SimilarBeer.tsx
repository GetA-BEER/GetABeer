import { BeerInfo } from './SimilarBeerController';
import Image from 'next/image';

export default function SimilarBeer(props: BeerInfo) {
  return (
    <div className="rounded-2xl w-full m-2 bg-white text-y-black drop-shadow-xl text-[5px] border">
      <div className="border-b-2 p-4">
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
