export const BeerCategoryMatcherToKor = (str: string) => {
  switch (str) {
    case 'ALE':
      return '에일';
    case 'LAGER':
      return '라거';
    case 'WEIZEN':
      return '밀맥주';
    case 'DUNKEL':
      return '흑맥주';
    case 'PILSENER':
      return '필스너';
    case 'FRUIT_BEER':
      return '과일';
    case 'NON_ALCOHOLIC':
      return '무알콜';
    case 'ETC':
      return '기타';
  }
};

export const BeerCountryMatcherToKor = (str: string) => {
  switch (str) {
    case 'ARGENTINA':
      return '아르헨티나';
    case 'AUSTRIA':
      return '오스트리아';
    case 'BELGIUM':
      return '벨기에';
    case 'CHINA':
      return '중국';
    case 'CZECH REPUBLIC':
      return '체코';
    case 'DENMARK':
      return '덴마크';
    case 'ENGLAND':
      return '영국';
    case 'FRANCE':
      return '프랑스';
    case 'GERMANY':
      return '독일';
    case 'HONG KONG':
      return '홍콩';
    case 'INDIA':
      return '인도';
    case 'IRELAND':
      return '아일랜드';
    case 'ITALY':
      return '이탈리아';
    case 'JAPAN':
      return '일본';
    case 'LATVIA':
      return '라트비아';
    case 'LITHUANIA':
      return '리투아니아';
    case 'MALAYSIA':
      return '말레이시아';
    case 'MEXICO':
      return '멕시코';
    case 'NETHERLANDS':
      return '네덜란드';
    case 'PHILIPPINES':
      return '필리핀';
    case 'SINGAPORE':
      return '싱가포르';
    case 'SOUTH KOREA':
      return '한국';
    case 'SPAIN':
      return '스페인';
    case 'SRI LANKA':
      return '스리랑카';
    case 'THAILAND':
      return '태국';
    case 'TURKEY':
      return '터키';
    case 'UNITED STATES':
      return '미국';
  }
};

export const BeerCategoryMatcher = (str: string[]) => {
  switch (str) {
    case ['에일']:
      return 'ALE';
    case ['라거']:
      return 'LAGER';
    case ['밀맥주']:
      return 'WEIZEN';
    case ['흑맥주']:
      return 'DUNKEL';
    case ['필스너']:
      return 'PILSENER';
    case ['과일']:
      return 'FRUIT_BEER';
    case ['무알콜']:
      return 'NON_ALCOHOLIC';
    case ['기타']:
      return 'ETC';
  }
};
