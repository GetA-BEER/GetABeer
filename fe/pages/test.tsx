import CommentInput from '@/components/inputs/CommentInput';
import SpeechBalloon from '@/components/SpeechBalloon';
import Footer from '@/components/Footer';
import MiddleCard, { testBeer } from '@/components/middleCards/MiddleCard';
import MonthlyCard from '@/components/middleCards/MonthlyCard';
import BigCard from '@/components/middleCards/BigCard';

export default function Test() {
  const comment = [
    {
      id: 1,
      nickName: '디자인 실장 유미님',
      date: '2023.02.02',
      ment: '디자인실장 유미님 멋져욧!!!',
    },
    {
      id: 2,
      nickName: '일타강사 유진님',
      date: '2023.02.02',
      ment: '일타강사 유진님 멋져욧!!! 만약에 코멘트가 진짜진짜진짜진짜 길어져서 이렇게 많은 글자를 포함해야 한다고 하더라도 말풍선이 예쁘게 나타나질지 모르겠네요!! 어렵지 않지만 왜이렇게 오래걸리는지!! 제가 게으르기때문일까요?ㅠㅠ 다들 엄청 빨리빨리 하시는데 저만 뒤쳐지는 것 같아 죄송하고 조급하고 속상하고 마음이 좋지 못하네요ㅠㅠ 잘해내고 싶은데 그만큼 노력하지 않는 내자신 진짜 반성해... 지금 이게 거의 400자정도 되는데 코멘트의 글자수 제한은 100자 이니까 두배정도 되겠군요! 그렇다면 이걸 두배로 붙이면 어떤 사람이 최대로 코멘트를 작성하더라도 요만해보인다는 뜻이겠지요 글자 크기를 줄일지 말지 고민이 되는데 이런 사소한 것 정도는 혼자 결정할 줄 아는 어른이 되어야겠지요? 무지성으로 이렇게 계속 글자를 쓰는게 오늘 하루 했던 일 들 중에 제일 재미있는 것 같습니다. 물론 여기까지 끝까지 읽는 사람은 저밖에 없을테지만 그래도 혹시라도 여기까지 읽는 사람이 있다면 새해복 많이 받으시고 올 한해도 행복한 일 가득 하시길 바라요. 복붙하려고 했는데 그냥 쓰다보니 벌써 천자를 채웠네요! 자소서도 이렇게 쓰면 금방 쓸텐데ㅎㅎ',
    },
  ];
  return (
    <div>
      <MiddleCard cardProps={testBeer} />
      <MonthlyCard cardProps={testBeer} />
      <BigCard cardProps={testBeer} />
      <div>코멘트 input</div>
      <CommentInput />
      <div>말풍선</div>
      {comment.map((el) => {
        return <SpeechBalloon key={el.id} props={el} />;
      })}
      <Footer />
    </div>
  );
}
