package com.xhr.configserver.schedule;

import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xhr.configserver.util.HttpUtil;

@Component
public class ConfigRefresh {

	@Value("${server.port:8080}")
	private String port;

	@Value("${spring.application.name:config}")
	private String appName;

	private HttpUtil hu = new HttpUtil();

	private boolean changed = false;

	@Autowired
	private List<WatchService> watchService;

	// @Scheduled(fixedRate = 3000)
	public void configRefresh() {
		String url = "http://localhost:" + port + "/" + appName + "/env";
		hu.simpleGetRequest(url);
	}

	// @Scheduled(fixedRate = 3000)
	public void localPathWatcher() {
		try {
			for (WatchService ws : watchService) {
				WatchKey watchKey = ws.poll(1, TimeUnit.MINUTES);
				if (watchKey != null) {
					watchKey.pollEvents().stream().forEach(event -> {
						if (!".svn".equalsIgnoreCase(event.context().toString())) {
							System.out.println(event.context());
							changed = true;
						}
					});
				}
				if (changed) {
					// 当SVN发生变化时,需要请求 bus/refresh 要求所有client刷新配置
					String url = "http://localhost:" + port + "/bus/refresh";
					hu.simplePostRequest(url);
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
