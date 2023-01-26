package be.domain.pairing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class PairingImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String imageUrl;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 */
	@ManyToOne
	@JoinColumn(name = "pairing_id")
	private Pairing pairing;

	/* 🧡 페어링 이미지 - 페어링 다대일 연관관계 편의 메서드 */
	public void belongToPairing(Pairing pairing) {
		this.pairing = pairing;
	}
}
