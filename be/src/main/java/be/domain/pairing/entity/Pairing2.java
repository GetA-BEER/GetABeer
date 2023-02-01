package be.domain.pairing.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;

import be.domain.beer.entity.Beer;
import be.domain.comment.entity.PairingComment;
import be.domain.user.entity.User;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Pairing2 extends BaseTimeEntity {

	@Id
	@Column(name = "paring_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	private PairingCategory pairingCategory;

	@ColumnDefault("0")
	private Integer likeCount;

	@ColumnDefault("0")
	private Integer commentCount;

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 */
	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingImage2> paringImageList;

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 편의 메서드 */
	public void addPairingImageList(PairingImage2 paringImage) {
		paringImageList.add(paringImage);

		if (paringImage.getPairing() != this) {
			paringImage.belongToPairing(this);
		}
	}

	public void saveDefault(List<PairingComment> pairingCommentList,
		Integer likeCount, Integer commentCount) {
		// this.paringImageList = paringImageList;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateCategory(PairingCategory pairingCategory) {
		this.pairingCategory = pairingCategory;
	}
}