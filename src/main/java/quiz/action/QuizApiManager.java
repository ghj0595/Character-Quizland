package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizApiManager {
	private static JSONObject getApiData(String uri) throws IOException {
		String restApiKey = "";
		try {
			Context init = new InitialContext();
			Context ctx = (Context) init.lookup("java:comp/env");
			restApiKey = (String) ctx.lookup("apiKey/TmdbApiKey");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		String authorization = String.format("Bearer %s", restApiKey);

		int maxRetries = 5;
		int retryCount = 0;
		long waitTime = 500;

		while (retryCount < maxRetries) {
			try {
				URL url = new URL(uri);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", authorization);

				InputStream in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				StringBuilder builder = new StringBuilder();

			    String line;
			    while ((line = reader.readLine()) != null) {
			        builder.append(line);
			    }

				reader.close();
				conn.disconnect();
				return new JSONObject(builder.toString());
			} catch (Exception e) {
				retryCount++;
				if (retryCount >= maxRetries) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				waitTime *= 2;
			}
		}
		return new JSONObject();
	}

	public static JSONObject getRandomContent(int type) throws IOException {
		Random ran = new Random();

		String typeStr = "movie";
		if (type == 1)
			typeStr = "tv";

//		int page=ran.nextInt(200)+1;//인기목록 페이지는 1~500으로 제한되어 있음
//		String uri = "https://api.themoviedb.org/3/";
//		uri += typeStr;
//		uri += "/popular?language=ko&page=";//인기목록 중에 가져옴
//		uri += page;

		// 요청처리가 짧은 시간에 여러번 하게 되면 누락될 때가 많아 재시도하는거로 수정
		String uri = "https://api.themoviedb.org/3/discover/";
		uri += typeStr;
		uri += "?language=ko";
		if (type == 0)
			uri += "&certification.lte=19&certification_country=KR";// 등급은 영화만 적용됌
		uri += "&sort_by=popularity.desc&with_origin_country=KR&without_genres=16%2C99%2C10763%2C10764%2C10767&page=";
		// 한국작품중에 한국 등급 19세 이하 애니메이션(16),다큐멘터리(99),뉴스(10763),리얼리티(10764),토크(10767)를 뺀 작품
		String uriPageChk = uri + "1";

		JSONObject pageData = getApiData(uriPageChk);

		int totalPage = pageData.getInt("total_pages");

		int page = ran.nextInt(totalPage - 1) + 1;

		uri += page;

		JSONObject data = getApiData(uri);

		JSONArray results = data.getJSONArray("results");

		int contentNum = ran.nextInt(results.length());

		return results.getJSONObject(contentNum);
	}

	public static int getRandomContentID(int type) throws IOException {
		JSONObject content = getRandomContent(type);

		return content.getInt("id");
	}

	public static JSONObject getContent(int type, int code) throws IOException {
		String typeStr = "movie";
		if (type == 1)
			typeStr = "tv";

		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/";
		uri += code;
		uri += "?language=ko";

		return getApiData(uri);
	}

	public static JSONArray getCast(int type, int code) throws IOException {
		String typeStr = "movie";
		if (type == 1)
			typeStr = "tv";

		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/";
		uri += code;
		uri += "/credits?language=ko";

		JSONObject data = getApiData(uri);

		return data.getJSONArray("cast");
	}

	public static int getPeopleID(int type, int code) throws IOException {
		JSONArray cast = getCast(type, code);
		if (cast.isEmpty())
			return 0;
		
		JSONObject people = cast.getJSONObject(0);

		return people.getInt("id");
	}

	public static JSONObject getPeople(int code) throws IOException {
		String uri = "https://api.themoviedb.org/3/person/";
		uri += code;
		uri += "?language=ko";

		return getApiData(uri);
	}
	
	public static String getTranslateName(String name, String overview) throws IOException{
        String apiKey = "";
		try {
			Context init = new InitialContext();
			Context ctx = (Context) init.lookup("java:comp/env");
			apiKey = (String) ctx.lookup("apiKey/OPENAIApiKey");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
        String uri = "https://api.openai.com/v1/chat/completions";

		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Authorization", "Bearer " + apiKey);
			conn.setDoOutput(true);
			
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", "줄거리가 " + overview  +" 인 캐릭터의 이름을 한국어로 번역해줘 그 이름은 : " + name);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("messages", new JSONArray().put(message));
            jsonBody.put("temperature", 0.7);
			
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
			
			int resCode = conn.getResponseCode();
			if(resCode == HttpURLConnection.HTTP_OK) {
				InputStream in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder builder = new StringBuilder();

			    String line;
			    while ((line = reader.readLine()) != null) {
			        builder.append(line);
			    }
				reader.close();
				conn.disconnect();
				JSONObject result = new JSONObject(builder.toString());
				
				return result.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
			}else {
				System.out.println("번역 요청 실패: " + resCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name+" - 번역실패";
	}
}
