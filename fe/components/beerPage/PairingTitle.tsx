import Link from 'next/link';

export default function PairingTitle(props: {
  pairngCount: number | undefined;
  beerId: number;
}) {
  // console.log(props.pairngCount);
  return (
    <div className="max-w-4xl flex justify-between mx-5 mt-4 mb-1">
      <div className="flex items-center">
        <span className="font-semibold mr-1 text-sm">페어링</span>
        <span className="text-xs">{props.pairngCount}</span>
      </div>
      {props?.pairngCount === 0 ? (
        <></>
      ) : (
        <Link href={`/allpairing/${props.beerId}`}>
          <span className="text-xs flex items-end">모두보기</span>
        </Link>
      )}
    </div>
  );
}
