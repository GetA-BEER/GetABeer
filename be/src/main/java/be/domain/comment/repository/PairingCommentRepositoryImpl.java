package be.domain.comment.repository;

import static be.domain.comment.entity.QPairingComment.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.QPairingCommentDto_Response;

public class PairingCommentRepositoryImpl implements PairingCommentCustomRepository {

	private final JPAQueryFactory queryFactory;

	public PairingCommentRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<PairingCommentDto.Response> findPairingCommentList(Long pairingId) {
		var list = queryFactory
			.select(new QPairingCommentDto_Response(
				pairingComment.pairing.id,
				pairingComment.id,
				pairingComment.user.id,
				pairingComment.user.nickname,
				pairingComment.content,
				pairingComment.createdAt,
				pairingComment.modifiedAt
			)).from(pairingComment)
			.where(pairingComment.pairing.id.eq(pairingId))
			.fetch();

		return list;
	}
}
