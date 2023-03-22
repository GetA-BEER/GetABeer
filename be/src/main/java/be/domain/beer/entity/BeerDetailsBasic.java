package be.domain.beer.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsBasic {

	@NotBlank
	private String korName;
	@NotBlank
	private String engName;
	@NotBlank
	private String country;
	@NotBlank
	private String thumbnail;
	@NotBlank
	private Double abv;
	private Integer ibu;

	@Builder
	public BeerDetailsBasic(String korName, String engName, String country,
		String thumbnail, Double abv, Integer ibu) {
		this.korName = korName;
		this.engName = engName;
		this.country = country;
		this.thumbnail = thumbnail;
		this.abv = abv;
		this.ibu = ibu;
	}
}
