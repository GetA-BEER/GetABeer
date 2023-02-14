package be.domain.comment.repository;

import static be.domain.comment.entity.QPairingComment.*;
import static be.domain.pairing.entity.QPairing.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.QPairingCommentDto_Response;
import be.domain.comment.entity.PairingComment;
import be.domain.user.entity.User;

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

	@Override
	public Page<PairingComment> findPairingCommentByUser(User user, Pageable pageable) {
		List<PairingComment> pairingComments = queryFactory
			.select(pairingComment)
			.from(pairingComment)
			.where(pairingComment.user.eq(user))
			.orderBy(pairingComment.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(pairing.count())
			.from(pairing)
			.fetchOne();

		return new PageImpl<>(pairingComments, pageable, total);
	}
}
