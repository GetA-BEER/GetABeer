import Head from 'next/head';
import Image from 'next/image';
import NavBar from '../../components/NavBar';

export default function Main() {
  return (
    <div className="h-screen m-auto max-w-4xl">
      <div className="border-2 border-red-200">
        <main className="m-auto">
          <div className="py-2 bg-gray-200 text-black">상단헤더</div>
          <div className="py-2 bg-gray-200 text-black">
            <Image
              src="/images/adv.jpg"
              alt="adv"
              width={500}
              height={500}
              priority
            />
            <Image
              // className=""
              src="/images/adv.jpg"
              alt="adv"
              width={500}
              height={500}
              priority
            />
          </div>
        </main>
        <NavBar />
        ddd
      </div>
    </div>
  );
}
