// import { BeerInfo } from './SimilarBeerController';
import Image from 'next/image';

interface BeerInfo {
  id: number;
  idx?: number;
  title: string;
  category: string;
  country: string;
  level: number;
  ibu: number;
  image: string;
}

export default function SimilarBeer(props: { similarBeer: BeerInfo }) {
  return (
    <div className="rounded-2xl w-full m-2 bg-white text-y-black drop-shadow-xl text-[5px] border">
      <div className="border-b-2 p-4">
        <div className="text-base font-semibold">{props.similarBeer.title}</div>
        <div>{`${props.similarBeer.category} / ${props.similarBeer.country} ${props.similarBeer.level}% ${props.similarBeer.ibu}IBU`}</div>
      </div>
      <Image
        className="pt-3 rounded-2xl m-auto"
        alt="Beer"
        src={`${props.similarBeer.image}`}
        width={300}
        height={300}
      />
    </div>
  );
}
