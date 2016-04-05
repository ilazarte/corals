package com.blm.corals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TestLoader {

	public List<String> http(String surl) {
		
		List<String> strs = new ArrayList<String>();
		URL website;
		
		try {
			website = new URL(surl);
			URLConnection connection = website.openConnection();
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				strs.add(inputLine);
			}

			in.close();

			return strs;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> file(String surl) {

		List<String> list = new ArrayList<String>();
		String fileloc = null;
		
		try {
			URL url = new URL(surl);
			fileloc = url.getFile();
			
			File file = new File(fileloc);
			FileReader fr;
			
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String str;

			while ((str = br.readLine()) != null) {
				list.add(str);
			}

			br.close();
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}

}
