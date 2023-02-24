package be.domain.pairing.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingRequestDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.entity.PairingImage;

@Mapper(componentModel = "spring")
public interface PairingMapper {
	default Pairing pairingPostDtoToPairing(PairingRequestDto.Post post) {
		if (post == null) {
			return null;
		}

		return Pairing.builder()
			.content(post.getContent())
			.pairingCategory(PairingCategory.to(post.getCategory()))
			.pairingCommentList(new ArrayList<>())
			.likeCount(0)
			.commentCount(0)
			.build();
	}

	default Pairing pairingPatchDtoToPairing(PairingRequestDto.Patch patch) {
		if (patch == null) {
			return null;
		}

		return Pairing.builder()
			.content(patch.getContent())
			.pairingCategory(PairingCategory.to(patch.getCategory()))
			.build();
	}

	default PairingResponseDto.Detail pairingToPairingResponseDto(Pairing pairing, Long beerId) {
		if (pairing == null) {
			return null;
		}

		return PairingResponseDto.Detail.builder()
			.beerId(beerId)
			.pairingId(pairing.getId())
			.userId(pairing.getUser().getId())
			.nickname(pairing.getUser().getNickname())
			.content(pairing.getContent())
			.imageList(getPairingImageList(pairing.getPairingImageList()))
			.commentList(getCommentList(pairing.getPairingCommentList()))
			.category(pairing.getPairingCategory())
			.likeCount(pairing.getLikeCount())
			.commentCount(pairing.getCommentCount())
			.createdAt(pairing.getCreatedAt())
			.modifiedAt(pairing.getModifiedAt())
			.build();
	}

	private List<PairingImageDto.Response> getPairingImageList(List<PairingImage> pairingImages) {
		if (pairingImages == null) {
			return new ArrayList<>();
		}

		List<PairingImageDto.Response> result = new ArrayList<>();

		for (PairingImage pairingImage : pairingImages) {
			PairingImageDto.Response response = PairingImageDto.Response.builder()
				.pairingImageId(pairingImage.getId())
				.imageUrl(pairingImage.getImageUrl())
				.fileName(pairingImage.getFileName())
				.build();

			result.add(response);
		}

		return result;
	}

	private List<PairingCommentDto.Response> getCommentList(List<PairingComment> commentList) {
		if (commentList.size() == 0) {
			return new ArrayList<>();
		}

		List<PairingCommentDto.Response> result = new ArrayList<>();

		for (PairingComment pairingComment : commentList) {
			PairingCommentDto.Response response = PairingCommentDto.Response.builder()
				.pairingId(pairingComment.getPairing().getId())
				.pairingCommentId(pairingComment.getId())
				.userId(pairingComment.getUser().getId())
				.nickname(pairingComment.getUser().getNickname())
				.content(pairingComment.getContent())
				.createdAt(pairingComment.getCreatedAt())
				.modifiedAt(pairingComment.getModifiedAt())
				.build();

			result.add(response);
		}

		return result;
	}

	default Page<PairingResponseDto.Total> pairingToPairingResponse(List<Pairing> pairings,
		PairingLikeRepository pairingLikeRepository) {
		return new PageImpl<>(pairings.stream()
			.map(pairing ->
				new PairingResponseDto.Total(
					pairing.getBeer().getId(),
					pairing.getId(),
					pairing.getUser().getId(),
					pairing.getUser().getNickname(),
					pairing.getContent(),
					pairing.getThumbnail(),
					pairing.getPairingCategory(),
					pairing.getLikeCount(),
					pairing.getCommentCount(),
					pairingLikeRepository.findPairingLikeUser(pairing.getId(), pairing.getUser().getId()) != 0,
					pairing.getCreatedAt(),
					pairing.getModifiedAt())
			).collect(Collectors.toList()));
	}
}
