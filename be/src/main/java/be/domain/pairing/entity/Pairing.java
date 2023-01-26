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

import org.hibernate.annotations.ColumnDefault;

import be.domain.beer.entity.Beer;
import be.domain.recomment.entity.PairingRecomment;
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
public class Pairing extends BaseTimeEntity {

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
	private Integer recommentCount;

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 */
	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingImage> paringImageList = new ArrayList<>();

	/* 🧡 페어링 - 페어링 이미지 일대다 연관관계 편의 메서드 */
	public void addPairingImage(PairingImage image) {
		paringImageList.add(image);

		if (image.getPairing() != this) {
			image.belongToPairing(this);
		}
	}

	/* 💙 페어링 - 맥주 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "beer_id")
	private Beer beer;

	/* 💙 페어링 - 맥주 다대일 연관관계 편의 메서드 */
	public void belongToBeer(Beer beer) {
		this.beer = beer;
	}

	/* 💚 페어링 - 페어링 대댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "pairing", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingRecomment> pairingRecommentList = new ArrayList<>();

	/* 💚 페어링 - 페어링 대댓글 일대다 연관관계 편의 메서드 */
	public void addPairingReommentList(PairingRecomment pairingRecomment) {
		pairingRecommentList.add(pairingRecomment);

		if (pairingRecomment.getPairing() != this) {
			pairingRecomment.belongToPairing(this);
		}
	}
}
