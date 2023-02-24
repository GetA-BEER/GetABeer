package be.domain.pairing.service.pattern;

public interface SortingState {
	public void orderByRecency();
	public void orderByComments();
	public void orderByLikes();
}
