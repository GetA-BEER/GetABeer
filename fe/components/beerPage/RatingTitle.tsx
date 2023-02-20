import Link from 'next/link';

export interface ratingCount {
  count?: number;
}

export default function RatingTitle(props: {
  ratingCount: number;
  beerId: number;
}) {
  return (
    <div className="max-w-4xl flex justify-between mx-5 mt-4 mb-1">
      <div className="flex items-center">
        <span className="font-semibold mr-1 text-sm">코멘트</span>
        <span className="text-xs">{props.ratingCount}</span>
      </div>
      {props?.ratingCount === 0 ? (
        <></>
      ) : (
        <Link href={`/rating/${props.beerId}`}>
          <span className="text-xs flex items-end">모두보기</span>
        </Link>
      )}
    </div>
  );
}
