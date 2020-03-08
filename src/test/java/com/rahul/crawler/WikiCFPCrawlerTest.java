package com.rahul.crawler;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

class WikiCFPCrawlerTest {

	private static final Integer NUM_CRAWLERS = 1;
	private static final String CRAWL_STORAGE_FILE_PATH = "src/main/resources/crawler4j";
	private static final String BIG_DATA_FILE_PATH = "src/test/resources/big_data";
	private static final String DATA_MINING_FILE_PATH = "src/test/resources/data_mining";
	private static final String MACHINE_LEARNING_FILE_PATH = "src/test/resources/machine_learning";
	private static final String AI_FILE_PATH = "src/test/resources/artificial_intelligence";

	private File crawlStorage;

	private CrawlController controller;

	@BeforeEach
	public void setup() throws Exception {

		this.crawlStorage = new File(CRAWL_STORAGE_FILE_PATH);
		this.controller = createCrawlController();
	}

	private CrawlController createCrawlController() throws Exception {
		CrawlConfig crawlconfig = this.getCrawlConfiguration();
		PageFetcher pageFetcher = new PageFetcher(crawlconfig);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(crawlconfig, pageFetcher, robotstxtServer);

		return controller;
	}

	private CrawlConfig getCrawlConfiguration() {
		CrawlConfig config = new CrawlConfig();
		config.setPolitenessDelay(7000);
//		config.setMaxDepthOfCrawling(20);
		config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
		return config;
	}

	@Test
	public void crawlBigDataConfrences() throws Exception {

		Files.deleteIfExists(Paths.get(BIG_DATA_FILE_PATH));
		FileWriter fileWriter = new FileWriter(BIG_DATA_FILE_PATH, true);

		controller.addSeed("http://www.wikicfp.com/cfp/call?conference=big%20data%20&page=1");

		Set<String> bigDataURLs = IntStream.range(1, 21)
				.mapToObj(i -> "http://www.wikicfp.com/cfp/call?conference=big%20data%20&page=" + i)
				.collect(Collectors.toSet());

		startCrawl(bigDataURLs, fileWriter);
	}

	@Test
	public void crawlDataMiningConfrences() throws Exception {
		Files.deleteIfExists(Paths.get(DATA_MINING_FILE_PATH));
		FileWriter fileWriter = new FileWriter(DATA_MINING_FILE_PATH, true);

		controller.addSeed("http://www.wikicfp.com/cfp/call?conference=data%20mining&page=1");

		Set<String> dataMiningUrls = IntStream.range(1, 21)
				.mapToObj(i -> "http://www.wikicfp.com/cfp/call?conference=data%20mining&page=" + i)
				.collect(Collectors.toSet());

		startCrawl(dataMiningUrls, fileWriter);
	}

	@Test
	public void crawlMachineLearningConfrences() throws Exception {
		Files.deleteIfExists(Paths.get(MACHINE_LEARNING_FILE_PATH));
		FileWriter fileWriter = new FileWriter(MACHINE_LEARNING_FILE_PATH, true);

		controller.addSeed("http://www.wikicfp.com/cfp/call?conference=machine%20learning&page=1");

		Set<String> mlUrls = IntStream.range(1, 21)
				.mapToObj(i -> "http://www.wikicfp.com/cfp/call?conference=machine%20learning&page=" + i)
				.collect(Collectors.toSet());

		startCrawl(mlUrls, fileWriter);
	}

	@Test
	public void crawlAIConfrences() throws Exception {
		Files.deleteIfExists(Paths.get(AI_FILE_PATH));
		FileWriter fileWriter = new FileWriter(AI_FILE_PATH, true);

		controller.addSeed("http://www.wikicfp.com/cfp/call?conference=artificial%20intelligence&page=1");

		Set<String> aiURLs = IntStream.range(1, 21)
				.mapToObj(i -> "http://www.wikicfp.com/cfp/call?conference=artificial%20intelligence&page=" + i)
				.collect(Collectors.toSet());

		startCrawl(aiURLs, fileWriter);
	}

	private void startCrawl(Set<String> bigDataURLs, FileWriter fileWriter) {
		CrawlController.WebCrawlerFactory<WikiCFPCrawler> factory = () -> {

			return new WikiCFPCrawler(bigDataURLs, fileWriter);
		};

		controller.start(factory, NUM_CRAWLERS);
	}

}
