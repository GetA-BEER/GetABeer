package be.domain.rating.mapper;

import java.util.HashMap;

import org.mapstruct.Mapper;

import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.entity.RatingTag;

@Mapper(componentModel = "spring")
public interface RatingTagMapper {
	default RatingTag ratingPostDtoToRatingTag(RatingRequestDto.Post post) {
		if (post == null) {
			return null;
		}

		return RatingTag.builder()
			.color(BeerTagType.valueOf(post.getColor()))
			.taste(BeerTagType.valueOf(post.getTaste()))
			.flavor(BeerTagType.valueOf(post.getFlavor()))
			.carbonation(BeerTagType.valueOf(post.getCarbonation()))
			.build();
	}

	default RatingTag ratingPatchDtoToRatingTag(RatingRequestDto.Patch patch) {
		HashMap<String, String> map = new HashMap<>();
		map.put("color", map.getOrDefault("color", "") + patch.getColor());
		map.put("taste", map.getOrDefault("taste", "") + patch.getTaste());
		map.put("flavor", map.getOrDefault("flavor", "") + patch.getFlavor());
		map.put("carbonation", map.getOrDefault("carbonation", "") + patch.getCarbonation());

		RatingTag.RatingTagBuilder tag = RatingTag.builder();

		String color = map.get("color");
		String taste = map.get("taste");
		String flavor = map.get("flavor");
		String carbonation = map.get("carbonation");

		if (color.equalsIgnoreCase("STRAW") || color.equalsIgnoreCase("GOLD")
			|| color.equalsIgnoreCase("BROWN") || color.equalsIgnoreCase("BLACK")) {
			tag.color(BeerTagType.valueOf(color));
		} else if (color.equals("")) {
			tag.color(null);
		}

		if (taste.equalsIgnoreCase("SWEET") || taste.equalsIgnoreCase("SOUR")
			|| taste.equalsIgnoreCase("BITTER") || taste.equalsIgnoreCase("ROUGH")) {
			tag.taste(BeerTagType.valueOf(taste));
		} else if (taste.equals("")) {
			tag.taste(null);
		}

		if (flavor.equalsIgnoreCase("FRUITY") || flavor.equalsIgnoreCase("FLOWER")
			|| flavor.equalsIgnoreCase("MALTY") || flavor.equalsIgnoreCase("HOPPY")) {
			tag.flavor(BeerTagType.valueOf(flavor));
		} else if (flavor.equals("")) {
			tag.flavor(null);
		}

		if (carbonation.equalsIgnoreCase("WEAK") || carbonation.equalsIgnoreCase("MIDDLE")
			|| carbonation.equalsIgnoreCase("STRONG") || carbonation.equalsIgnoreCase("NO_CARBONATION")) {
			tag.carbonation(BeerTagType.valueOf(carbonation));
		} else if (carbonation.equals("")) {
			tag.carbonation(null);
		}

		return tag.build();
	}
}
