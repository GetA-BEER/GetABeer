import Link from 'next/link';

export default function MapNav({ curTab }: { curTab: 0 | 1 }) {
  return (
    <nav className="flex justify-around mb-3">
      <Link href={'/map/store'}>
        <div
          className={`text-xl lg:text-2xl ${
            curTab === 0
              ? 'text-y-brown border-b-2 border-y-brown'
              : 'text-y-lightGray'
          }`}
        >
          편의점
        </div>
      </Link>
      <Link href={'/map/brewery'}>
        <div
          className={`text-xl lg:text-2xl ${
            curTab === 1
              ? 'text-y-brown border-b-2 border-y-brown'
              : 'text-y-lightGray'
          }`}
        >
          브루어리
        </div>
      </Link>
    </nav>
  );
}
