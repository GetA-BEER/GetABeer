package be.domain.user.entity;

import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.chatting.entity.ChatMessage;
import be.domain.chatting.entity.ChatRoom;
import be.domain.comment.entity.BeerComment;
import be.domain.pairing.entity.Pairing;
import be.domain.recomment.entity.BeerRecomment;
import be.domain.recomment.entity.PairingRecomment;
import be.domain.user.dto.UserDto;
import be.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

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
    private String gender;

    @Column
    private int age;

    public Users(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }


    public void edit(String nickname) {
        this.nickname = nickname;
    }

    /* UserBeerTag Join */
    @OneToMany(mappedBy = "users")
    private List<UserBeerTag> userBeerTags;

//    /* BeerWishlist 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
//    private List<BeerWishlist> beerWishlists;
//
//    /* BeerComment 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users")
//    private List<BeerComment> beerComments;
//
//    /* BeerRecomment 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users")
//    private List<BeerRecomment> beerRecomments;
//
//    /* Pairing 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users")
//    private List<Pairing> pairings;
//
//    /* PairingRecomment 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users")
//    private List<PairingRecomment> pairingRecomments;
//
//    /* ChatRoom 1:1 양방향 매핑 */
//    @OneToOne(mappedBy = "users", cascade = CascadeType.REMOVE)
//    private ChatRoom chatRoom;
//
//    /* ChatMessage 1:N 양방향 매핑 */
//    @OneToMany(mappedBy = "users")
//    private List<ChatMessage> chatMessages;
}
