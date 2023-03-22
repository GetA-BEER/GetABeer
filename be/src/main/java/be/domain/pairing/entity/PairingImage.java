package be.domain.pairing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PairingImage {

	protected PairingImage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false)
	private String fileName;

	@Range(min = 1, max = 3)
	private Integer imagesOrder;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "pairing_id")
	private Pairing pairing;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 편의 메서드 */
	public void belongToPairing(Pairing pairing) {
		this.pairing = pairing;
	}

	@Builder
	public PairingImage(Long id, String imageUrl, String fileName, Pairing pairing) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.fileName =fileName;
		this.pairing = pairing;
	}

	public void setImagesOrder(Integer imagesOrder) {
		this.imagesOrder = imagesOrder;
	}
}
