package be.global.image;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;

public interface ImageHandler {
	HashMap createProfileImage(MultipartFile file, String folderSrc) throws IOException;

	List<PairingImage> createPairingImage(Pairing pairing,
		List<MultipartFile> files, Long userId, Beer beer) throws IOException;

	List<PairingImage> updatePairingImage(Pairing pairing, List<String> type, List<Long> url,
		List<MultipartFile> files) throws IOException;
	void deleteProfileImage(String fileKey, String folderSrc);

	HashMap updateProfileImage(MultipartFile file, String folderSrc, String oldFileKey) throws IOException;
}
