package be.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.pairing.entity.Pairing;
import be.domain.rating.entity.Rating;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Column
	private String imageUrl;

	@Column
	private String provider;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Age age;

	public User(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	@Builder
	public User(Long id, String email, String nickname,
				String password, List<String> roles,
				String provider, String imageUrl) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.roles = roles;
		this.provider = provider;
		this.imageUrl = imageUrl;
	}

	public void edit(UserDto.EditUserInfo editUserInfo) {
		this.imageUrl = editUserInfo.getImageUrl() == null ? this.getImageUrl() : editUserInfo.getImageUrl();
		this.nickname = editUserInfo.getNickname() == null ? this.getNickname() : editUserInfo.getNickname();
		this.gender = editUserInfo.getGender() == null ? this.getGender() : editUserInfo.getGender();
		this.age = editUserInfo.getAge() == null ? this.getAge() : editUserInfo.getAge();
		// this.userBeerTags = editUserInfo.getUserBeerTags() == null ? this.userBeerTags : editUserInfo.getUserBeerTags();
	}

	public void setUserInfo(UserDto.UserInfoPost post) {
		this.age = post.getAge();
		this.gender = post.getGender();
		// this.userBeerTags = post.getUserBeerTags();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	/* UserBeerTag Join */
	@OneToMany(mappedBy = "user")
	private List<UserBeerTag> userBeerTags;

	/* BeerWishlist 1:N 양방향 매핑 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<BeerWishlist> beerWishlists;

	/* 🤎회원 - 맥주 평가 일대다 연관관계 */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Rating> ratingList;

	/* 🤎회원 - 맥주 평가 일대다 연관관계 편의 메서드 */
	public void addRatingList(Rating rating) {
		ratingList.add(rating);

		if (rating.getUser() != this) {
			rating.bndUser(this);
		}
	}

	/* 💝 회원 - 맥주 평가 댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<RatingComment> ratingCommentList;

	/* 💝 회원 - 맥주 평가 댓글 일대다 연관관계 */
	public void addRatingCommentList(RatingComment ratingComment) {
		ratingCommentList.add(ratingComment);

		if (ratingComment.getUser() != this) {
			ratingComment.bndUser(this);
		}
	}

	/* 🖤 회원 - 페어링 일대다 연관관계 */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Pairing> pairingList;

	/* 🖤 회원 - 페어링 일대다 연관관계 편의 메서드 */
	public void addPairingList(Pairing pairing) {
		pairingList.add(pairing);

		if (pairing.getUser() != this) {
			pairing.bndUser(this);
		}
	}

	/* 💙회원 - 페어링 댓글 일대다 연관관계 */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<PairingComment> pairingCommentList;

	/* 💙회원 - 페어링 댓글 일대다 연관관계 편의 메서드 */
	public void addPairingCommentList(PairingComment pairingComment) {
		pairingCommentList.add(pairingComment);

		if (pairingComment.getUser() != this) {
			pairingComment.bndUser(this);
		}
	}

	//    /* ChatRoom 1:1 양방향 매핑 */
	//    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
	//    private ChatRoom chatRoom;
	//
	//    /* ChatMessage 1:N 양방향 매핑 */
	//    @OneToMany(mappedBy = "user")
	//    private List<ChatMessage> chatMessages;
}
