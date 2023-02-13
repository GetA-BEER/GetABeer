import Link from 'next/link';
import Image from 'next/image';

export default function Footer() {
  return (
    <footer className="w-full h-44 bg-y-cream">
      <div className="flex flex-col mx-4 py-6">
        <span className="text-black text-xs mb-7">copyright © Get-A-Beer</span>

        <Link
          href={'https://github.com/GetA-BEER/GetABeer'}
          className="flex items-center"
        >
          <Image
            width={24}
            height={24}
            src="/images/github.svg"
            alt="github_logo"
          />
          <span className=" text-y-black text-sm ml-1">github</span>
        </Link>
      </div>
    </footer>
  );
}
