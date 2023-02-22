package be.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfoWithBeerInfo {
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private Long beerId;
	private String beerKorName;
	private String beerEngName;
}
