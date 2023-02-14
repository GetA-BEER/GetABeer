package be.global.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {

	@Pointcut("execution(* be.domain.beer.service.BeerServiceImpl.getBeer(..))")
	public void getBeer() {
	}

	@Pointcut("execution(* be.domain.rating.service.RatingService.create(..))")
	public void createRating() {
	}

	@Pointcut("execution(* be.domain.rating.service.RatingService.update(..))")
	public void updateRating() {
	}

	@Pointcut("execution(* be.domain.rating.service.RatingService.delete(..))")
	public void deleteRating() {
	}

	@Pointcut("execution(* be.domain.pairing.service.PairingService.create(..))")
	public void createPairing() {
	}

	@Pointcut("execution(* be.domain.pairing.service.PairingService.update(..))")
	public void updatePairing() {
	}

	@Pointcut("execution(* be.domain.pairing.service.PairingService.delete(..))")
	public void deletePairing() {
	}

}