package be.domain.pairing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class PairingImage {

	protected PairingImage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String imageUrl1;

	@Column
	private String imageUrl2;

	@Column
	private String imageUrl3;

	/* 🧡 페어링 이미지 - 페어링 일대일 연관관계 */
	@OneToOne
	@JoinColumn(name = "pairing_id")
	private Pairing pairing;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 편의 메서드 */
	public void oneToOneByPairing(Pairing pairing) {
		this.pairing = pairing;

		if (pairing.getParingImage() != this) {
			pairing.oneToOneByPairingImage(this);
		}
	}

	@Builder
	public PairingImage(Long id, String imageUrl1, String imageUrl2, String imageUrl3) {
		this.id = id;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
	}
}
