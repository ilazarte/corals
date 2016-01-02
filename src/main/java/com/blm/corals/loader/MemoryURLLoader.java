package com.blm.corals.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blm.corals.CoralUtils;

/**
 * Implements a simple map to contain instances of data loaded by another loader.
 * @author perico
 */
public class MemoryURLLoader implements URLLoader {

	private CoralUtils utils = new CoralUtils();
	
	private URLLoader loader;
	
	private Map<String, List<String>> cache = new HashMap<String, List<String>>();
	
	public MemoryURLLoader(URLLoader liveURLLoader) {
		this.loader = liveURLLoader;
	}

	@Override
	public List<String> load(String url) {
		String filename = utils.filename(url);
		List<String> list = cache.get(filename);
		if (list == null) {
			list = loader.load(url);
			cache.put(filename, list);
		}
		return list;
	}

}
