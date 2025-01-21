package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TMDBApiManager {
	private static JSONObject getApiData(String uri) throws IOException{
		String restApiKey = "";
		try {
			Context init = new InitialContext();
			Context ctx = (Context) init.lookup("java:comp/env");
			restApiKey = (String) ctx.lookup("apiKey/TmdbApiKey");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		String authorization = String.format("Bearer %s", restApiKey);
		
		URL url= new URL(uri);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", authorization);
		
		InputStream in = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		StringBuilder builder = new StringBuilder();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		reader.close();
		conn.disconnect();
		
        return new JSONObject(builder.toString());
	}
	
	
	public static JSONObject getRandomContent(int type) throws IOException{
		Random ran = new Random();

		String typeStr="movie";
		if(type==1)
			typeStr="tv";

		int page=ran.nextInt(200)+1;//인기목록 페이지는 1~500으로 제한되어 있음
		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/popular?language=ko&page=";//인기목록 중에 가져옴
		uri += page;

//		// 요청처리가 짧은 시간에 여러번 하게 되면 누락될 때가 많음
//		String uri = "https://api.themoviedb.org/3/discover/";
//		uri += typeStr;
//		uri += "?language=ko";
//		if(type==0)
//			uri += "&certification.lte=19&certification_country=KR";//등급은 영화만 적용됌
//		uri += "&sort_by=popularity.desc&with_origin_country=KR&without_genres=16%2C99%2C10763%2C10764%2C10767&page=";
//		//한국작품중에 한국 등급 19세 이하 애니메이션(16),다큐멘터리(99),뉴스(10763),리얼리티(10764),토크(10767)를 뺀 작품
//		String uriPageChk=uri+"1";
//		
//		JSONObject pageData = getApiData(uriPageChk);
//		
//		int totalPage= pageData.getInt("total_pages");
//		
//		int page=ran.nextInt(totalPage-1)+1;
//		
//		uri+=page;

		JSONObject data = getApiData(uri);
		
		JSONArray results = data.getJSONArray("results");
		
		int contentNum=ran.nextInt(results.length());
		
		return results.getJSONObject(contentNum);
	}

	public static int getRandomContentID(int type) throws IOException{
		JSONObject content = getRandomContent(type);
		
		return content.getInt("id");
	}
	
	public static JSONObject getContent(int type, int code) throws IOException{
		String typeStr="movie";
		if(type==1)
			typeStr="tv";

		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/";
		uri += code;
		uri += "?language=ko";
		
		return getApiData(uri);
	}
	
	public static JSONArray getCast(int type, int code) throws IOException {
		String typeStr="movie";
		if(type==1)
			typeStr="tv";

		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/";
		uri += code;
		uri += "/credits?language=ko";
		
		JSONObject data = getApiData(uri);
		
		return data.getJSONArray("cast");
	}
	
	public static int getPeopleID(int type, int code) throws IOException {
		JSONArray cast = getCast(type,code);
		
		JSONObject people = cast.getJSONObject(0);
		
		return people.getInt("id");
	}
	
	public static JSONObject getPeople(int code) throws IOException{
		String uri = "https://api.themoviedb.org/3/person/";
		uri += code;
		uri += "?language=ko";
		
		return getApiData(uri);
	}
}
