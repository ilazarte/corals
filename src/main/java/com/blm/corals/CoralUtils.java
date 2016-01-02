package com.blm.corals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoralUtils {

	public String filename(String url) {
		return url.replaceAll("[^\\w]", "-");
	}
	
	public void writeFile(String filename, List<String> data) {
		File file = new File(filename);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (String line : data) {
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			fw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
			}
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	public List<String> readFile(String filename) {
		
		List<String> list = new ArrayList<String>();
		File file = new File(filename);
		FileReader fr;
		try {
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
