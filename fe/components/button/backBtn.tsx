import { useRouter } from 'next/router';
import { IoChevronBack } from 'react-icons/io5';

export default function BackBtn() {
  const router = useRouter();
  return (
    <IoChevronBack
      onClick={() => router.back()}
      className="text-xl text-y-gray mt-2"
    />
  );
}
