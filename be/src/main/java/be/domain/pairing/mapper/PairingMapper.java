package be.domain.pairing.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import be.domain.pairing.dto.PairingDto;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.global.dto.SingleResponseDto;

@Mapper(componentModel = "spring")
public interface PairingMapper {
	Pairing pairingPostDtoToPairing(PairingDto.Post post);

	PairingImage pairingPostDtoToPairingImage(PairingDto.Post post);

	Pairing pairingPatchDtoToPairing(PairingDto.Patch patch);

	default PairingDto.Response pairingToPairingResponseDto(Pairing pairing, Long beerId) {
		if (pairing == null) {
			return null;
		}

		PairingDto.Response response = PairingDto.Response.builder()
			.beerId(beerId)
			.pairingId(pairing.getId())
			.nickname(pairing.getNickname())
			.content(pairing.getContent())
			.imageList(getPairingImageDto(pairing))
			.category(pairing.getPairingCategory())
			.likeCount(pairing.getLikeCount())
			.recommentCount(pairing.getRecommentCount())
			.createdAt(pairing.getCreatedAt())
			.modifiedAt(pairing.getModifiedAt())
			.build();

		return response;
	}

	private List<PairingImageDto.Response> getPairingImageDto(Pairing pairing) {
		if (pairing.getParingImage() == null) {
			return null;
		}
		PairingImageDto.Response response = PairingImageDto.Response.builder()
			.imageUrl1(pairing.getParingImage().getImageUrl1())
			.imageUrl2(pairing.getParingImage().getImageUrl2())
			.imageUrl3(pairing.getParingImage().getImageUrl3())
			.build();

		return List.of(response);
	}

	List<PairingDto.Response> pairingToPairingResponseDto(List<Pairing> pairingList);
}
