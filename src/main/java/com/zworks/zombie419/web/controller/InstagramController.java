package com.zworks.zombie419.web.controller;

import javax.annotation.PostConstruct;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.exceptions.InstagramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zworks.zombie419.web.model.FrontResponse;

@Controller
@RequestMapping("channel/instagram")
public class InstagramController {
	
	 @Value("${instagram.clientid}")
	 private String clientid;
	 
	 @Value("${instagram.secret}")
	 private String secret;
	 
	 @Value("${zombie419.host}")
	 private String host;
	 
	 private InstagramService service;
	 
	 private Token accessToken;
	 
	 public static final Logger log = LoggerFactory.getLogger(InstagramController.class);
	 
	 @PostConstruct
	 public void initService() {
		 String callbackUrl = getCallBackUrl();
		 service = new InstagramAuthService()
					.apiKey(clientid)
					.apiSecret(secret)
					.callback(callbackUrl)
					.scope("public_content")
					.build();
	 }

	@GetMapping(value="oauth/authorize")
	public String authorize() {
		return "redirect:"+service.getAuthorizationUrl();
	}
	
	@GetMapping(value="oauth/verify")
	public String verify(@RequestParam String code) {
		Verifier verifier = new Verifier(code);
		accessToken = service.getAccessToken(verifier);
		return "index";
	}
	
	private String getCallBackUrl() {
		return host+"/channel/instagram/oauth/verify";
	}
	
	@ResponseBody
	@GetMapping(value="/users/self/media/recent")
	public FrontResponse getUserRecentMedia() {
		FrontResponse response = new FrontResponse();
		Instagram instagram = new Instagram(accessToken);
		try {
			MediaFeed feed = instagram.getUserRecentMedia();
			response.success(feed);
		} catch (InstagramException e) {
			log.error(e.getMessage(), e);
			response.failed("调用接口失败");
		}
		return response;
	}

	
}
