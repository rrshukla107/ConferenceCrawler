package com.rahul.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class WikiCFPCrawler extends WebCrawler {
	private static final Logger CRAWL_LOGGER = LoggerFactory.getLogger("CrawlerLogs");
	private static final Logger DATA_LOGGER = LoggerFactory.getLogger("CrawlerData");

	private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
	private Set<String> urlsToVisit;
	private FileWriter fileWriter;

	public WikiCFPCrawler(Set<String> urlsToVisit, FileWriter fileWriter) {
		this.urlsToVisit = urlsToVisit;
		this.fileWriter = fileWriter;

	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {

		String urlString = url.getURL().toLowerCase();
		return !EXCLUSIONS.matcher(urlString).matches() && this.urlsToVisit.contains(urlString);

	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		CRAWL_LOGGER.info("VISITED :: " + url);
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			Document document = Jsoup.parse(html);
			Elements select = document.select(
					"body > div.contsec > center > form > table > tbody > tr:nth-child(3) > td > table > tbody td[align=\"left\"]");

			try {
				this.writeDataToFile(select, this.fileWriter);
			} catch (IOException e) {
				CRAWL_LOGGER.error("Error in Parsing", e);
			}

		}

	}

	private void writeDataToFile(Elements select, FileWriter fileWriter) throws IOException {
		for (Element element : select.toArray(new Element[select.size()])) {
			DATA_LOGGER.info(element.text());
			fileWriter.write(element.text());
			fileWriter.write("\n");
			fileWriter.flush();
		}
	}
}
