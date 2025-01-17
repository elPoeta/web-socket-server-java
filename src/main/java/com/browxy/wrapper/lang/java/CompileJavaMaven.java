package com.browxy.wrapper.lang.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.browxy.wrapper.lang.CompilerCode;
import com.browxy.wrapper.message.JavaMessage;
import com.browxy.wrapper.message.Message;

public class CompileJavaMaven implements CompilerCode {
	private static final Logger logger = LoggerFactory.getLogger(CompileJavaMaven.class);

	@Override
	public boolean compileUserCode(Message message) {
		try {
			JavaMessage javaMessage = (JavaMessage) message;
			String containerBasePath = System.getProperty("containerBasePath");
			String customRepoPath = System.getProperty("mavenRepoPath") != null ? System.getProperty("mavenRepoPath")
					: "/srv/maven";
			String customSettingsPath = System.getProperty("mavenSettingsPath") != null
					? System.getProperty("mavenSettingsPath")
					: "/srv/maven/settings.xml";
		    Path path = Paths.get(javaMessage.getUserCodePath());    
	        Path directory = path.getParent();
			String filePath = containerBasePath + "" + directory.getFileName().toString();
			ProcessBuilder builder = new ProcessBuilder("mvn", "-Dmaven.repo.local=" + customRepoPath, "-s",
					customSettingsPath, "clean", "compile");
			builder.directory(new File(filePath));
			builder.redirectErrorStream(true);
			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}

			int exitCode = process.waitFor();
			return exitCode == 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error maven compile", e);
		}
		return false;
	}

}
