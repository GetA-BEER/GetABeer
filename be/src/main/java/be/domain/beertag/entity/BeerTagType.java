package be.domain.beertag.entity;

public enum BeerTagType {

	/*
	 * COLOR
	 */
	STRAW(), // 짚
	GOLD(),  // 금색
	BROWN(), // 갈색
	BLACK(), // 검은색
	/*
	 * TASTE
	 */
	SWEET(), // 단맛
	SOUR(), // 신맛
	BITTER(), // 쓴맛
	ROUGH(), // 떫은맛
	/*
	 * FLAVOR
	 */
	FRUITY(), // 과일향
	FLOWER(), // 꽃향
	MALTY(), // 몰트향
	NO_SCENT(), // 무향
	/*
	 * CARBONATION
	 */
	WEAK(), // 약함
	MIDDLE(), // 중간
	STRONG(), // 강함
	NO_CARBONATION(); // 없음
}
