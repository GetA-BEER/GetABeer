import Link from 'next/link';
import Image from 'next/image';

export default function Footer() {
  return (
    <footer className="w-full h-[120px] bg-y-black">
      <div className="flex flex-col  mx-4 py-6">
        <span className="text-y-lightGray text-xs mb-7">
          copyright © Get-A-Beer
        </span>

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
          <span className=" text-y-gray text-sm ml-1">github</span>
        </Link>
      </div>
    </footer>
  );
}
