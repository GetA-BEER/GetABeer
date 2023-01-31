package be.domain.crawler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrawledInfo {

    @Id
    @Column(name = "crawling_information_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String engName;
    private String country;
    private String beerCategory;
    private String abv;
    private String ibu;
}
