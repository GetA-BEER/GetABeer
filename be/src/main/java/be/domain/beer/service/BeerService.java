package be.domain.beer.service;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeerService {

    @Transactional
    public Beer createBeer(Beer beer) {
        return null;
    }

    @Transactional
    public Beer updateBeer(Beer beer, Long beerId) {
        return null;
    }

    private void beerCategoryToBeer(Beer beer, Beer savedBeer) {

    }

    public Beer findBeer(Long beerId) {
        return null;
    }

    public void deleteBeer(Long beerId) {

    }

//    public Beer isWishListedBeer(Beer beer, User user){
//        return null;
//    }

//    public Comment isLikedComment(Comment comment, User user){
//        return null;
//    }

//    public Beer isLikedComments(Long beerId){
//        return null;
//    }

    public List<MonthlyBeer> findMonthlyBeers() {
        return null;
    }

    public List<Beer> findSimilarBeers(Beer beer) {
        return null;
    }

    public Page<Beer> findMyPageBeers(Integer page) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return null;
    }

    public Beer findVerifiedBeer(Long beerId) {
        return null;
    }
}
