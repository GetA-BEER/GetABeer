export const TagMatcherToKor = (str: string) => {
  switch (str) {
    case 'GOLD':
      return '금색';
    case 'SWEET':
      return '단맛';
    case 'FLOWER':
      return '꽃향';
    case 'STRONG':
      return '탄산 강';
  }
};

export const TagMatcherToEng = (str: string) => {
  switch (str) {
    case '짚색':
      return 'GOLD';
    case '금색':
      return 'GOLD';
    case '갈색':
      return 'BROWN';
    case '흑색':
      return 'BLACK';
    case '과일향':
      return 'FLOWER';
    case '꽃향':
      return 'FLOWER';
    case '맥아향':
      return 'FLOWER';
    case '無향':
      return 'FLOWER';
    case '단맛':
      return 'SWEET';
    case '신맛':
      return 'SWEET';
    case '쓴맛':
      return 'SWEET';
    case '떫은맛':
      return 'SWEET';
    case '탄산 약':
      return 'STRONG';
    case '탄산 중':
      return 'STRONG';
    case '탄산 강':
      return 'STRONG';
    case '탄산 無':
      return 'STRONG';
  }
};
