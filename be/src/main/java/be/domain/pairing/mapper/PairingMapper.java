package be.domain.pairing.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingRequestDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;

@Mapper(componentModel = "spring")
public interface PairingMapper {
	Pairing pairingPostDtoToPairing(PairingRequestDto.Post post);

	Pairing pairingPatchDtoToPairing(PairingRequestDto.Patch patch);

	default PairingResponseDto.Detail pairingToPairingResponseDto(Pairing pairing, Long beerId) {
		if (pairing == null) {
			return null;
		}

		PairingResponseDto.Detail response = PairingResponseDto.Detail.builder()
			.beerId(beerId)
			.pairingId(pairing.getId())
			.nickname(pairing.getNickname())
			.content(pairing.getContent())
			.imageList(getPairingImageList(pairing.getPairingImageList()))
			.category(pairing.getPairingCategory())
			.likeCount(pairing.getLikeCount())
			.commentCount(pairing.getCommentCount())
			.createdAt(pairing.getCreatedAt())
			.modifiedAt(pairing.getModifiedAt())
			.build();

		return response;
	}

	private List<PairingImageDto.Response> getPairingImageList(List<PairingImage> pairingImages) {
		if (pairingImages == null) {
			return new ArrayList<>();
		}

		List<PairingImageDto.Response> result = new ArrayList<>();

		for (int i = 0; i < pairingImages.size(); i++) {
			PairingImageDto.Response response = PairingImageDto.Response.builder()
				.pairingImageId(pairingImages.get(i).getId())
				.imageUrl(pairingImages.get(i).getImageUrl())
				.build();

			result.add(response);
		}

		return result;
	}
}
