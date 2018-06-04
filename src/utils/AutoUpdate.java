package utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import dao.LinkDao;
import dao.NewsDao;
import entities.Link;
import entities.News;
import services.AndroidPushNotificationsService;

@Controller
public class AutoUpdate extends Thread {
	@Autowired
	private LinkDao linkDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private UtilString utilString;
	private int count = 0;

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	public AutoUpdate() {
		System.out.println("start");
		this.start();
	}

	@Override
	public void run() {
		while (true) {

			try {
				sleep(180000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Long start = Calendar.getInstance().getTimeInMillis();
			try {
				ArrayList<Link> arLink = (ArrayList<Link>) linkDao.getItems();
				for (Link link : arLink) {
					loadMoreHL(link.getId());
				}
			} catch (Exception e) {

			}
			if (count > 0) {
				send(count);
			}
			Long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println((end - start) / 1000);
		}

	}

	private String loadMoreHL(int idLink) {
		Link objLink = linkDao.getItem(idLink);
		try {
			Document document = Jsoup.connect(objLink.getLink()).get();
			for (Element element : document.select("item")) {
				String description = element.select("description").text();
				String title = element.select("title").text();
				String link = element.select("link").text();
				String UrlPicture = Jsoup.parse(description).select("img").first().absUrl("src");
				String preview = utilString.getDescription(description);
				Timestamp dateCreated = utilString.convertTimstamp(element.select("pubDate").text());
				if (!"".equals(preview)) {
					preview = Jsoup.parse(preview).text();
					News objNews = new News(0, title, preview, link, UrlPicture, dateCreated, objLink.getIdCat(), "",
							objLink.getIdResource(), "");
					if (newsDao.check(objNews)) {
						if (newsDao.addItem(objNews) > 0) {
							count++;
						} else {
							break;
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		return "";
	}

	public ResponseEntity<String> send(int count) throws JsonIOException {
		JsonObject body = new JsonObject();
		body.addProperty("to", "/topics/news");
		body.addProperty("priority", "high");

		JsonObject notification = new JsonObject();
		notification.addProperty("title", "TinHangNgay thông báo");
		notification.addProperty("body", count);

		JsonObject data = new JsonObject();
		data.addProperty("count", count);

		body.add("notification", notification);
		body.add("data", data);

		/**
		 * { "notification": { "title": "TinHangNgay thông báo", "body": "Happy
		 * Message!" }, "data": { "Key-1": "JSA Data 1", "Key-2": "JSA Data 2" }, "to":
		 * "/topics/news", "priority": "high" }
		 */

		HttpEntity<String> request = new HttpEntity<>(body.toString());
		System.out.println(body.toString());
		CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
}
