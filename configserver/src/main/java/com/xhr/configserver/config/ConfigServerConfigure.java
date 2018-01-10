package com.xhr.configserver.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xhr.config")
public class ConfigServerConfigure {

	private List<String> pathToWatch;

	public List<String> getPathToWatch() {
		return pathToWatch;
	}

	public void setPathToWatch(List<String> pathToWatch) {
		this.pathToWatch = pathToWatch;
	}
}
