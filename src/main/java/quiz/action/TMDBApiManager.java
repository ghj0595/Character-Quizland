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
	public static int getRandomPopularContent(int type) throws IOException{
		Random ran = new Random();

//		int page=ran.nextInt(499)+1;//인기목록 페이지는 1~500으로 제한되어 있음
		int page=ran.nextInt(200)+1;//한국 검색 목록 제한으로 333페이지 정보만 나옴
		
		String typeStr="movie";
		if(type==1)
			typeStr="tv";
//		String uri = "https://api.themoviedb.org/3/";
//		uri += typeStr;
//		uri += "/popular?language=ko-KR&page=";//인기목록 중에 가져옴
//		uri += page;
		String uri = "https://api.themoviedb.org/3/discover/";
		uri += typeStr;
		uri += "?language=ko-KR&sort_by=popularity.desc&with_origin_country=KR&without_genres=16%2C10764&page=";//한국작품중에 리얼리티 애니를 뺀 작품 
		uri += page;
		
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
		
		JSONObject resTMDBData = new JSONObject(builder.toString());
		
		JSONArray results = resTMDBData.getJSONArray("results");
		
		int contentNum=ran.nextInt(results.length());
		
		JSONObject content = results.getJSONObject(contentNum);
		System.out.println(content);
		System.out.println(typeStr);

		int contentId=content.getInt("id");
		
		return contentId;
	}

	public static int getPeopleID(int type, int code) throws IOException {
		String typeStr="movie";
		if(type==1)
			typeStr="tv";

		String uri = "https://api.themoviedb.org/3/";
		uri += typeStr;
		uri += "/";
		uri += code;
		uri += "/credits?language=ko-KR";
		
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
		
		JSONObject resTMDBData = new JSONObject(builder.toString());
		
		JSONArray results = resTMDBData.getJSONArray("cast");
		
		JSONObject people = results.getJSONObject(0);

		int peopleId=people.getInt("id");
		
		return peopleId;
	}
}
