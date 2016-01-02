package com.blm.corals.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HTTPURLLoader implements URLLoader {

	@Override
	public List<String> load(String surl) {
		
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

}
