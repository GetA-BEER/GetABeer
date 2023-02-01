package be.domain.pairing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class PairingImage2 {

	protected PairingImage2() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String imageUrl;

	/* 🧡 페어링 이미지 - 페어링 일대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "pairing_id")
	private Pairing2 pairing;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 편의 메서드 */
	public void belongToPairing(Pairing2 pairing) {
		this.pairing = pairing;
	}

	@Builder
	public PairingImage2(Long id, String imageUrl, Pairing2 pairing) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.pairing = pairing;
	}

	public void updateImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}