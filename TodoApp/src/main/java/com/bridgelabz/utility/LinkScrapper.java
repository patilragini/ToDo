package com.bridgelabz.utility;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.bridgelabz.Model.UrlData;




// TODO: Auto-generated Javadoc
/**
 * The Class LinkScrapper.
 */
@Component
public class LinkScrapper {

	/**
	 * Gets the url meta data.
	 *
	 * @param url the url
	 * @return the url meta data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public UrlData getUrlMetaData(String url) throws IOException {

		String title = null;
		String imageUrl = null;
		String domain=null;
		try {
			URI uri=new URI(url);
			domain=uri.getHost();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document document = Jsoup.connect(url).get();

		Elements metaOgTitle = document.select("meta[property=og:title]");

		// check og title if not availabe check title if not then set to empty string!
		if (metaOgTitle != null) {
			title = metaOgTitle.attr("content");

			if (title == null) {
				title = document.title();

			}
		}

		Elements metaOgImage = document.select("meta[property=og:image]");

		if (metaOgImage != null) {
			imageUrl = metaOgImage.attr("content");			
		}
		
		return new UrlData(title, imageUrl,domain);
	}

}
