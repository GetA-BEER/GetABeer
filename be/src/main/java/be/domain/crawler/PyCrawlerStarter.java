package be.domain.crawler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@RequiredArgsConstructor
public class PyCrawlerStarter {
	@Value("${python.crawler}")
	private String crawlerPath;

	@Bean
	public void run() throws IOException {
		new ProcessBuilder("python3", crawlerPath).start();
	}
}
