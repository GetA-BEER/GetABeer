package be.domain.pairing.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.pairing.dto.PairingDto;
import be.domain.pairing.entity.Pairing;

@Mapper(componentModel = "spring")
public interface PairingMapper {
	Pairing pairingPostDtoToPairing(PairingDto.Post post);

	Pairing pairingPatchDtoToPairing(PairingDto.Patch patch);

	default PairingDto.Response pairingToPairingResponseDto(Pairing pairing) {
		if (pairing == null) {
			return null;
		}

		PairingDto.Response response = PairingDto.Response.builder()
			.pairingId(pairing.getId())
			.nickname(pairing.getNickname())
			.content(pairing.getContent())
			.imageList(pairing.getParingImageList())
			.category(pairing.getPairingCategory())
			.likeCount(pairing.getLikeCount())
			.recommentCount(pairing.getRecommentCount())
			.createdAt(pairing.getCreatedAt())
			.modifiedAt(pairing.getModifiedAt())
			.build();

		return response;
	}

	List<PairingDto.Response> pairingToPairingResponseDto(List<Pairing> pairingList);
}
