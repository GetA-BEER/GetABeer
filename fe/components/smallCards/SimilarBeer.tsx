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
    <div className="rounded-2xl w-full mx-2 bg-white text-y-black drop-shadow-xl border mb-16">
      <div className="border-b-2 p-4">
        <div className="text-sm font-semibold">{props.similarBeer.title}</div>
        <div className="text-[8px]">{`${props.similarBeer.category}/${props.similarBeer.country} ${props.similarBeer.level}% ${props.similarBeer.ibu}IBU`}</div>
      </div>
      <Image
        className="pt-3 rounded-2xl m-auto"
        alt="Beer"
        src={`${props.similarBeer.image}`}
        width={100}
        height={100}
      />
    </div>
  );
}
