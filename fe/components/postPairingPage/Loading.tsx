import Image from 'next/image';
export default function Loading() {
  return (
    <>
      <div className="flex items-center justify-center relative">
        <div
          className="z-[2] inline-block h-16 w-16 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_2s_linear_infinite]"
          role="status"
        ></div>
        <Image
          className="absolute"
          src="/images/logo.png"
          alt="logo"
          width={66}
          height={66}
        />
      </div>
    </>
  );
}
