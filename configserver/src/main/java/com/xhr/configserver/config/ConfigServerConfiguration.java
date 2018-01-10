package com.xhr.configserver.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xhr.configserver.util.HttpUtil;

@Configuration
public class ConfigServerConfiguration {

	@Autowired
	private ConfigServerConfigure csc;

	private HttpUtil hu = new HttpUtil();

	public WatchService watchSVN(String path_watch) {
		try {
			System.out.println("watchSVN " + path_watch);
			final Path path = Paths.get(path_watch);
			WatchService watchService = path.getFileSystem().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			return watchService;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public List<WatchService> getWatchService() {
		List<WatchService> watchService = new LinkedList<WatchService>();
		List<String> localPath = csc.getPathToWatch();
		for (String path : localPath) {
			File fp = new File(path);
			if (!fp.exists()) {
				fp.mkdirs();
			}
			watchService.add(watchSVN(path));
		}
		return watchService;
	}
}
