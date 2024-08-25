package com.colvir.rss_news_parser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "scheduler.enabled=false")
class RssNewsParserApplicationTests {

	@Test
	void contextLoads() {
	}

}
