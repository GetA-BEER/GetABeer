import { useRouter } from 'next/router';
import { IoChevronBack } from 'react-icons/io5';

export default function BackBtn() {
  const router = useRouter();
  return (
    <IoChevronBack
      onClick={() => router.back()}
      className="text-xl m-4 w-6 h-6 cursor-pointer"
    />
  );
}
