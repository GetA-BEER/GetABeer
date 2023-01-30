package be.domain.user.entity;

import be.domain.beertag.entity.BeerTag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBeerTag {

    @Id
    @Column(name = "user_beer_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_tag_id")
    private BeerTag beerTag;

    public void addUser(Users users) {
        this.users = users;
        if (!this.users.getUserBeerTags().contains(this)) {
            this.users.getUserBeerTags().add(this);
        }
    }
}
