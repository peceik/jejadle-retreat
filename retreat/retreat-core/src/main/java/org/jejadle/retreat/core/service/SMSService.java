package org.jejadle.retreat.core.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jejadle.retreat.core.model.Message;
import org.jejadle.retreat.core.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
@PropertySource("retreat.properties")
public class SMSService {
	
	
	@Autowired
	MessageRepository messageRepo;

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(SMSService.class);
	
	
	public String sendMessage(String senderName, String content, String receiveNumber){
		
		return sendMessage(senderName,content,receiveNumber,0);
	}
	
	public String sendMessage(String senderName, String content, String receiveNumber, int retreatId){
		
		Message message = send(senderName, content, receiveNumber);
		message.setRetreatId(retreatId);
		messageRepo.save(message);
		
		return message.getHttpStatus();
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Transactional
	public Message  send(String senderName, String content, String receiveNumber){
		
		String hostname = "api.bluehouselab.com";
        String url = "https://"+hostname+"/smscenter/v1.0/sendsms";
        
        //전화번호 -, _ 삭제
        receiveNumber=StringUtils.remove(receiveNumber, "-");
        receiveNumber=StringUtils.remove(receiveNumber, "_");
        
       
        
        Message msg = new Message(env.getProperty("sms.senderNumber"), senderName,content,receiveNumber);
        
        if("id".equals(env.getProperty("sms.appid"))){
        	logger.info("pleas check retreat.properties sms.id");
        	msg.setHttpStatus("500");
            msg.setResult("정확한 SMS ID 를 입력하여 주세요");
        	
        	return msg;
        	
        }
        
        
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(hostname, 443, AuthScope.ANY_REALM),
            new UsernamePasswordCredentials(env.getProperty("sms.appid"), env.getProperty("sms.apikey"))
        );

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        authCache.put(new HttpHost(hostname, 443, "https"), new BasicScheme());

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        DefaultHttpClient client = new DefaultHttpClient();

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            String json = "{\"sender\":\""+env.getProperty("sms.senderNumber")+"\",\"receivers\":[\""+receiveNumber+"\"],\"content\":\""+content+"\"}";

            StringEntity se = new StringEntity(json, "UTF-8");
            httpPost.setEntity(se);

            HttpResponse httpResponse = client.execute(httpPost, context);
            
            logger.info("SMS Message Status Code:{}", httpResponse.getStatusLine().getStatusCode());
            msg.setHttpStatus(String.valueOf(httpResponse.getStatusLine().getStatusCode()));

            InputStream inputStream = httpResponse.getEntity().getContent();
            StringBuffer sb = new StringBuffer();
            if(inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    //System.out.println(line);
                	sb.append(line);
                }
                inputStream.close();
            }
            logger.info("SMS Messag Result:{}", sb.toString());
            msg.setResult(sb.toString());
            
        } catch (Exception e) {
            System.err.println("Error: "+e.getLocalizedMessage());
            logger.error("{}", e.toString()+e.getLocalizedMessage());
            msg.setHttpStatus("500");
            msg.setResult(e.getLocalizedMessage());
        } finally {
            client.getConnectionManager().shutdown();
            return msg;
        }
		
		
	}
	
	


}
