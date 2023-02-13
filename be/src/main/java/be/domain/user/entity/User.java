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
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.entity.enums.RandomProfile;
import be.domain.user.entity.enums.UserStatus;
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
public class User {

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

	@Column
	private String userStatus;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	/* UserBeerCategory Join */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<UserBeerCategory> userBeerCategories;

	/* User - UserBeerCategory 연관관계 편의 메서드 */
	public void addUserBeerCategories(UserBeerCategory userBeerCategory) {
		userBeerCategories.add(userBeerCategory);

		if (userBeerCategory.getUser() != this) {
			userBeerCategory.addUser(this);
		}
	}

	/* UserBeerTag Join */
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<UserBeerTag> userBeerTags;

	/* User - UserBeerTag 연관관계 편의 메서드 */
	public void addUserBeerTags(UserBeerTag userBeerTag) {
		userBeerTags.add(userBeerTag);

		if (userBeerTag.getUser() != this) {
			userBeerTag.addUser(this);
		}
	}

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

	public User(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	@Builder
	public User(Long id, String email, String nickname,
		String password, List<String> roles,
		String provider, String imageUrl,
		String status) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.roles = roles;
		this.provider = provider;
		this.imageUrl = imageUrl;
		this.userStatus = status;
	}

	public void edit(String imageUrl, String nickname, Gender gender, Age age) {
		this.imageUrl = imageUrl == null ? this.imageUrl : imageUrl;
		this.nickname = nickname == null ? this.nickname : nickname;
		this.gender = gender == null ? this.gender : gender;
		this.age = age == null ? this.age : age;
	}

	public void setUserInfo(Age age, Gender gender) {
		this.age = age;
		this.gender = gender;
	}

	public void setUserBeerTags(List<UserBeerTag> userBeerTags) {
		this.userBeerTags = this.userBeerTags == null ? userBeerTags : this.userBeerTags;
	}

	public void setUserBeerCategories(List<UserBeerCategory> userBeerCategories) {
		this.userBeerCategories = this.userBeerCategories == null ? userBeerCategories : this.userBeerCategories;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void editPassword(String password) {
		this.password = password;
	}

	public void withdraw() {
		this.userStatus = UserStatus.QUIT_USER.getStatus();
	}

	public void randomProfileImage(String imageUrl) {
		this.imageUrl =
			imageUrl == null ? RandomProfile.values()[(int)(Math.random() * 4)].getValue() : this.imageUrl;
	}

	public void setProvider() {
		this.provider = this.provider == null ? String.valueOf(ProviderType.LOCAL) : this.provider;
	}
}
