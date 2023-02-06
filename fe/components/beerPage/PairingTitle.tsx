export interface ratingCount {
  count?: number;
}

export default function PairingTitle(props: { pairngCount: number }) {
  return (
    <div className="max-w-4xl flex justify-between mx-5 my-4">
      <div className="flex items-center">
        <span className="font-semibold mr-1 text-sm">페어링</span>
        <span className="text-xs">{props.pairngCount}</span>
      </div>
      <span className="text-xs">모두보기</span>
    </div>
  );
}
