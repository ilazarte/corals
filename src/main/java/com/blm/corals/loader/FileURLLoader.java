package com.blm.corals.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileURLLoader implements URLLoader {

	@Override
	public List<String> load(String surl) {

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
