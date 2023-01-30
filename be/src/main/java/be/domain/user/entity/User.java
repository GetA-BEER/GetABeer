package be.domain.user.entity;

import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.chatting.entity.ChatMessage;
import be.domain.chatting.entity.ChatRoom;
import be.domain.comment.entity.BeerComment;
import be.domain.pairing.entity.Pairing;
import be.domain.recomment.entity.BeerRecomment;
import be.domain.recomment.entity.PairingRecomment;
import be.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column
    private String imageUrl;

    @Column
    private String gender;

    @Column
    private int age;

    public User(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    /* UserBeerTag Join */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserBeerTag> userBeerTags;

    /* BeerWishlist 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BeerWishlist> beerWishlists;

    /* BeerComment 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user")
    private List<BeerComment> beerComments;

    /* BeerRecomment 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user")
    private List<BeerRecomment> beerRecomments;

    /* Pairing 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user")
    private List<Pairing> pairings;

    /* PairingRecomment 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user")
    private List<PairingRecomment> pairingRecomments;

    /* ChatRoom 1:1 양방향 매핑 */
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ChatRoom chatRoom;

    /* ChatMessage 1:N 양방향 매핑 */
    @OneToMany(mappedBy = "user")
    private List<ChatMessage> chatMessages;
}
