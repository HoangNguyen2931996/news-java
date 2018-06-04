package controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import services.AndroidPushNotificationsService;

@RestController
public class WebController {
	
	private final String TOPIC = "news";
	
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
 
	@RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> send() throws JsonIOException {
		JsonObject body = new JsonObject();
		body.addProperty("to", "/topics/" + TOPIC);
		body.addProperty("priority", "high");
 
		JsonObject notification = new JsonObject();
		notification.addProperty("title", "JSA Notification");
		notification.addProperty("body", "Happy Message!");
		
		JsonObject data = new JsonObject();
		data.addProperty("Key-1", "JSA Data 1");
		data.addProperty("Key-2", "JSA Data 2");
 
		body.add("notification", notification);
		body.add("data", data);
 
/**
		{
		   "notification": {
		      "title": "JSA Notification",
		      "body": "Happy Message!"
		   },
		   "data": {
		      "Key-1": "JSA Data 1",
		      "Key-2": "JSA Data 2"
		   },
		   "to": "/topics/JavaSampleApproach",
		   "priority": "high"
		}
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
