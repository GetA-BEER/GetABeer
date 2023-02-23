import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import ColorTag from '@/components/tag/ColorTag';
import SmellTag from '@/components/tag/SmellTag';
import TasteTag from '@/components/tag/TasteTag';
import CarbonatinTag from '@/components/tag/CarbonationTag';
import BigInput from '@/components/inputs/BigInput';
import CloseBtn from '@/components/button/CloseBtn';
import SubmitBtn from '@/components/button/SubmitBtn';
import MiddleCard, {
  MiddleCardInfo,
} from '@/components/middleCards/MiddleCard';
import { TagMatcherToEng, TagMatcherToKor } from '@/utils/TagMatcher';
import axios from '@/pages/api/axios';
import StarRating from '@/components/inputs/StarRating';
import PageContainer from '@/components/PageContainer';

export default function EditRatingPage() {
  const router = useRouter();
  const ratingId = router.query.ratingid;
  const [star, setStar] = useState(0);
  const [content, setContent] = useState('');
  const [color, setColor] = useState('');
  const [flavor, setFlavor] = useState('');
  const [taste, setTaste] = useState('');
  const [carbonation, setCarbonation] = useState('');
  const [cardProps, setCardProps] = useState<any>();

  const getBeerInfo = (beerId: number) => {
    if (beerId !== undefined) {
    }
  };

  useEffect(() => {
    if (ratingId !== undefined) {
      axios.get(`/api/ratings/${ratingId}`).then((res) => {
        setStar(res.data.star);
        setContent(res.data.content);
        setColor(res.data.ratingTag[0]);
        setFlavor(res.data.ratingTag[1]);
        setTaste(res.data.ratingTag[2]);
        setCarbonation(res.data.ratingTag[3]);
        axios.get(`/api/beers/${res.data.beerId}`).then((res) => {
          const beerIInfo: MiddleCardInfo = {
            beerId: res.data.beerId,
            thumbnail: res.data.beerDetailsBasic.thumbnail,
            korName: res.data.beerDetailsBasic.korName,
            category: res.data.beerCategoryTypes,
            country: res.data.beerDetailsBasic.country,
            abv: res.data.beerDetailsBasic.abv,
            ibu: res.data.beerDetailsBasic.ibu,
            totalStarCount:
              res.data.beerDetailsCounts.totalStarCount ||
              res.data.beerDetailsCounts.femaleStarCount +
                res.data.beerDetailsCounts.maleStarCount,
            totalAverageStars: res.data.beerDetailsStars.totalAverageStars,
            beerTags: res.data.beerDetailsTopTags || [],
          };
          setCardProps(beerIInfo);
        });
      });
    }
  }, [ratingId]);

  const handleSubmit = () => {
    const reqBody = {
      beerId: 1,
      userId: 1,
      star,
      content,
      color: TagMatcherToEng(color),
      flavor: TagMatcherToEng(flavor),
      taste: TagMatcherToEng(taste),
      carbonation: TagMatcherToEng(carbonation),
    };
    axios.patch(`/api/ratings/${ratingId}`, reqBody).then((res) => {
      router.replace(`/rating/${ratingId}`);
    });
  };

  return (
    <PageContainer>
      <main className="px-6">
        <MiddleCard cardProps={cardProps} />
        <div>
          <div className="mt-5">
            <div>별점</div>
            <div className="flex justify-start items-center">
              <div className="flex items-center h-10 mb-8 -mt-3">
                <StarRating star={star} setStar={setStar} />
              </div>
              <span className="text-2xl ml-[260px] mt-2">{star}</span>
            </div>
          </div>
          <div className="mt-3">
            <div>평가</div>
            <ColorTag setSelected={setColor} checked={TagMatcherToKor(color)} />
            <SmellTag
              setSelected={setFlavor}
              checked={TagMatcherToKor(flavor)}
            />
            <TasteTag setSelected={setTaste} checked={TagMatcherToKor(taste)} />
            <CarbonatinTag
              setSelected={setCarbonation}
              checked={TagMatcherToKor(carbonation)}
            />
          </div>
          <div className="mt-5">
            <div className="mb-3">리뷰</div>
            <BigInput
              placeholder="맥주에 대한 평가를 남겨주세요"
              inputState={content}
              setInputState={setContent}
            />
          </div>
          <div className="flex -ml-1">
            <div className="flex-1">
              <CloseBtn onClick={() => router.back()}>나가기</CloseBtn>
            </div>
            <div className="flex-1">
              {star === 0 ? (
                <div className="flex justify-center items-center w-full h-11 rounded-xl m-2 bg-red-100 text-xs text-red-500 -ml-[1px]">
                  별점과 평가를 선택해주세요
                </div>
              ) : (
                <SubmitBtn onClick={handleSubmit}>수정하기</SubmitBtn>
              )}
            </div>
          </div>
        </div>
      </main>
    </PageContainer>
  );
}
