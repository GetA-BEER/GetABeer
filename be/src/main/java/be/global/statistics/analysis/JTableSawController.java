package be.global.statistics.analysis;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/jtable"})
@RequiredArgsConstructor
public class JTableSawController {
	private final JTableSawService jTableSawService;

	@GetMapping("/test")
	public void jTableSawTest() throws IOException {
		jTableSawService.test();
	}
}
