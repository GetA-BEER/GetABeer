package be.global.image;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;

public interface ImageHandler {
	String createUserImage(MultipartFile file) throws IOException;
	List<PairingImage> createPairingImage(Pairing pairing,
		List<MultipartFile> files, Long userId, Beer beer) throws IOException;
}
