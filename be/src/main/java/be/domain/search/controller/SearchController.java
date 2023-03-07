package be.domain.search.controller;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.follow.FollowQueryRepository;
import be.domain.search.service.SearchService;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
	private final SearchService searchService;
	private final UserService userService;
	private final BeerMapper beerMapper;
	private final UserMapper userMapper;
	private final FollowQueryRepository followQueryRepository;

	@GetMapping
	public ResponseEntity<MultiResponseDto<?>> getSearchResult(
		@RequestParam("query") String queryParam,
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		queryParam = queryParam.strip();

		if (queryParam.charAt(0) == '@') {

			Page<User> userPage = searchService.findUsersPageByQueryParam(queryParam, page);
			PageImpl<UserDto.UserSearchResponse> responsePage;

			if (userService.getLoginUserReturnNull() != null) {

				User findUser = userService.getLoginUser();
				Long userId = findUser.getId();

				responsePage = userMapper.userToUserSearchResponses(userPage, followQueryRepository, userId);
			} else {

				responsePage = userMapper.userToUserSearchResponses(userPage, followQueryRepository);
			}

			return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), userPage));

		} else {

			Page<Beer> beerPage = searchService.findBeersPageByQueryParam(queryParam, page);

			PageImpl<BeerDto.SearchResponse> responsePage = beerMapper.beersPageToSearchResponse(beerPage);

			return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), beerPage));
		}
	}
}
