export const SearchMatcherToEng = (str: string) => {
  switch (str) {
    case '@에일':
      return '@ALE';
    case '@라거':
      return '@LAGER';
    case '@밀맥주':
      return '@WEIZEN';
    case '@흑맥주':
      return '@DUNKEL';
    case '@필스너':
      return '@PILSENER';
    case '@과일':
      return '@FRUIT_BEER';
    case '@무알콜':
      return '@NON_ALCOHOLIC';
    case '@기타':
      return '@ETC';
    case '#짚색':
      return '#STRAW';
    case '#금색':
      return '#GOLD';
    case '#갈색':
      return '#BROWN';
    case '#흑색':
      return '#BLACK';
    case '#과일향':
      return '#FRUITY';
    case '#꽃향':
      return '#FLOWER';
    case '#맥아향':
      return '#MALTY';
    case '#無향':
      return '#NO_SCENT';
    case '#단맛':
      return '#SWEET';
    case '#신맛':
      return '#SOUR';
    case '#쓴맛':
      return '#BITTER';
    case '#떫은맛':
      return '#ROUGH';
    case '#탄산 약':
      return '#WEAK';
    case '#탄산 중':
      return '#MIDDLE';
    case '#탄산 강':
      return '#STRONG';
    case '#탄산 無':
      return '#NO_CARBONATION';
    default:
      return str;
  }
};

export const SearchMatcherToKor = (str: string | string[] | undefined) => {
  switch (str) {
    case '@ALE':
      return '@에일';
    case '@LAGER':
      return '@라거';
    case '@WEIZEN':
      return '@밀맥주';
    case '@DUNKEL':
      return '@흑맥주';
    case '@PILSENER':
      return '@필스너';
    case '@FRUIT_BEER':
      return '@과일';
    case '@NON_ALCOHOLIC':
      return '@무알콜';
    case '@ETC':
      return '@기타';
    case '#STRAW':
      return '#짚색';
    case '#GOLD':
      return '#금색';
    case '#BROWN':
      return '#갈색';
    case '#BLACK':
      return '#흑색';
    case '#SWEET':
      return '#단맛';
    case '#SOUR':
      return '#신맛';
    case '#BITTER':
      return '#쓴맛';
    case '#ROUGH':
      return '#떫은맛';
    case '#FRUITY':
      return '#과일향';
    case '#FLOWER':
      return '#꽃향';
    case '#MALTY':
      return '#맥아향';
    case '#NO_SCENT':
      return '#無향';
    case '#WEAK':
      return '#탄산 약';
    case '#MIDDLE':
      return '#탄산 중';
    case '#STRONG':
      return '#탄산 강';
    case '#NO_CARBONATION':
      return '#탄산 無';
    default:
      return str;
  }
};
