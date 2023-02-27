export const TagMatcherToKor = (str: string) => {
  switch (str) {
    case 'STRAW':
      return '짚색';
    case 'GOLD':
      return '금색';
    case 'BROWN':
      return '갈색';
    case 'BLACK':
      return '흑색';
    case 'SWEET':
      return '단맛';
    case 'SOUR':
      return '신맛';
    case 'BITTER':
      return '쓴맛';
    case 'ROUGH':
      return '떫은맛';
    case 'FRUITY':
      return '과일향';
    case 'FLOWER':
      return '꽃향';
    case 'MALTY':
      return '맥아향';
    case 'NO_SCENT':
      return '無향';
    case 'WEAK':
      return '탄산 약';
    case 'MIDDLE':
      return '탄산 중';
    case 'STRONG':
      return '탄산 강';
    case 'NO_CARBONATION':
      return '탄산 無';
  }
};

export const TagMatcherToEng = (str: string) => {
  switch (str) {
    case '짚색':
    case 'STRAW':
      return 'STRAW';
    case '금색':
    case 'GOLD':
      return 'GOLD';
    case '갈색':
    case 'BROWN':
      return 'BROWN';
    case '흑색':
    case 'BLACK':
      return 'BLACK';
    case '과일향':
    case 'FRUITY':
      return 'FRUITY';
    case '꽃향':
    case 'FLOWER':
      return 'FLOWER';
    case '맥아향':
    case 'MALTY':
      return 'MALTY';
    case '無향':
    case 'NO_SCENT':
      return 'NO_SCENT';
    case '단맛':
    case 'SWEET':
      return 'SWEET';
    case '신맛':
    case 'SOUR':
      return 'SOUR';
    case '쓴맛':
    case 'BITTER':
      return 'BITTER';
    case '떫은맛':
    case 'ROUGH':
      return 'ROUGH';
    case '탄산 약':
    case 'WEAK':
      return 'WEAK';
    case '탄산 중':
    case 'MIDDLE':
      return 'MIDDLE';
    case '탄산 강':
    case 'STRONG':
      return 'STRONG';
    case '탄산 無':
    case 'NO_CARBONATION':
      return 'NO_CARBONATION';
  }
};

export const TagMatcherToEngArr = (str: string[]) => {
  switch (str) {
    case ['짚색']:
      return 'STRAW';
    case ['금색']:
      return 'GOLD';
    case ['갈색']:
      return ['BROWN'];
    case ['흑색']:
      return ['BLACK'];
    case ['과일향']:
      return 'FRUITY';
    case ['꽃향']:
      return 'FLOWER';
    case ['맥아향']:
      return 'MALTY';
    case ['無향']:
      return 'NO_SCENT';
    case ['단맛']:
      return 'SWEET';
    case ['신맛']:
      return 'SOUR';
    case ['쓴맛']:
      return 'BITTER';
    case ['떫은맛']:
      return 'ROUGH';
    case ['탄산 약']:
      return 'WEAK';
    case ['탄산 중']:
      return 'MIDDLE';
    case ['탄산 강']:
      return 'STRONG';
    case ['탄산 無']:
      return 'NO_CARBONATION';
  }
};
