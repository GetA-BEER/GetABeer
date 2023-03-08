import Image from 'next/image';
export interface FollowProps {
  nickname: string;
  imageUrl: string;
  isFollowing: boolean;
}
export default function FollowUser(props: { followprops: FollowProps }) {
  return (
    <div className="py-1 px-2 m-2 flex justify-between ">
      <div className="flex gap-2">
        <Image
          className="h-11 w-11 m-auto mr-1 self-center rounded-full"
          src={props.followprops.imageUrl}
          alt="프로필사진"
          width={40}
          height={40}
        />
        <div className="self-center text-sm">{props.followprops.nickname}</div>
      </div>
      <div className="self-center">
        <div className="bg-y-cream py-2 px-8 rounded-lg text-xs ">팔로우</div>
      </div>
    </div>
  );
}
