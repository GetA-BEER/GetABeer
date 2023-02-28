export interface BeerInfo {
  beerId: number;
  beerDetailsBasic: {
    korName: string;
    engName: string;
    country: string;
    thumbnail: string;
    abv: number;
    ibu: number;
  };
  beerCategoryTypes: string[];
  beerDetailsTopTags: string[] | null;
  beerDetailsStars: {
    totalAverageStars: number;
    femaleAverageStars: number;
    maleAverageStars: number;
  };
  beerDetailsCounts: {
    totalStarCount: number;
    femaleStarCount: number;
    maleStarCount: number;
    ratingCount: number;
    pairingCount: number;
  };
  isWishListed: boolean;
  similarBeers: BeerInfo[];
}

export interface RatingInfo {
  data: RatingCardProps[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    beerId: number;
    beerKorName: string;
    beerEngName: string;
  };
}

export interface RatingCardProps {
  beerId: number;
  ratingId: number;
  korName: string;
  userId: number;
  nickname: string;
  userImage: string;
  star: number;
  ratingTag: [string, string, string, string];
  content: string;
  likeCount: number;
  commentCount: number;
  createdAt: string;
  modifiedAt: string;
  isUserLikes: boolean;
}

export interface PairingInfo {
  data: PairingCardProps[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    beerId: number;
    beerKorName: string;
    beerEngName: string;
  };
}

export interface PairingCardProps {
  beerId: number;
  korName: string;
  pairingId: number;
  nickname: string;
  userImage: string;
  content: string;
  thumbnail: string;
  category: string;
  likeCount: number;
  commentCount: number;
  isUserLikes: boolean;
  createdAt: string;
  modifiedAt: string;
}

export interface SimilarBeerProps {
  beerId: number;
  korName: string;
  country: string;
  beerCategories: [
    {
      beerCategoryType: string;
    }
  ];
  averageStar: number;
  starCount: number;
  thumbnail: string;
  abv: number;
  ibu: number | null;
}

export interface PopularBeerType {
  beerId: number;
  korName: string;
  thumbnail: string;
  averageStar: number;
  beerCategories: any;
  country: string;
  ibu: number | null;
  abv: number;
}

export interface RecommendBeerType {
  averageStar: number;
  beerCategories: any;
  beerId: number;
  korName: string;
  thumbnail: string;
  country: string;
  ibu: number | null;
  abv: number;
}
