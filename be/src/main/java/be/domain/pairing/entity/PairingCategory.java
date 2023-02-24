package be.domain.pairing.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PairingCategory implements CategoryHelper {
	FRIED(1, "FRIED"),
	GRILL(2, "GRILL"),
	STIR(3, "STIR"),
	FRESH(4, "FRESH"),
	DRY(5, "DRY"),
	SNACK(6, "SNACK"),
	SOUP(7, "SOUP"),
	ETC(8, "ETC");

	private final int number;
	private final String category;

	PairingCategory(int number, String category) {
		this.number = number;
		this.category = category;
	}

	@JsonCreator
	public static PairingCategory to(String category) {
		log.info("**************************************************************");
		log.info("등록하려는 카테고리 : " + category);
		log.info("**************************************************************");

		for (PairingCategory pairingCategory : PairingCategory.values()) {
			log.info("**************************************************************");
			log.info("반복문 안");
			log.info("페어링 카테고리 확인 : " + pairingCategory.getCategory());
			log.info("**************************************************************");
			if (pairingCategory.getCategory().equalsIgnoreCase(category)) {
				log.info("**************************************************************");
				log.info("조건문 안");
				log.info("**************************************************************");
				return pairingCategory;
			}
		}
		log.info("************************예외 던질 예정**************************************");
		throw new BusinessLogicException(ExceptionCode.NOT_FOUND_CATEGORY);
	}
	@Override
	public int getNumber() {
		return number;
	}

	@JsonValue
	public String getCategory() {
		return category;
	}
}
