package be.domain.pairing.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.pairing.dto.PairingDto;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.Pairing2;
import be.domain.pairing.entity.PairingImage;

@Mapper(componentModel = "spring")
public interface PairingMapper2 {

	Pairing2 pairingPost2DtoToPairing2(PairingDto.Post2 post2);

	Pairing2 pairingPatchDtoToPairing(PairingDto.Patch2 patch);

	default PairingDto.Response2 pairingToPairingResponseDto(Pairing2 pairing,
		List<PairingImageDto.Response2> response) {
		if (pairing == null) {
			return null;
		}

		PairingDto.Response2 result = PairingDto.Response2.builder()
			.pairingId(pairing.getId())
			.nickname(pairing.getNickname())
			.content(pairing.getContent())
			.imageList(response)
			.category(pairing.getPairingCategory())
			.likeCount(pairing.getLikeCount())
			.commentCount(pairing.getCommentCount())
			.createdAt(pairing.getCreatedAt())
			.modifiedAt(pairing.getModifiedAt())
			.build();

		return result;
	}
}
