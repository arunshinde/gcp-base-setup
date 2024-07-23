package com.gcp.cloud.poc.instance;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gcp.cloud.firestore.FirestoreExecute;

import jakarta.servlet.http.HttpServlet;

@Configuration
public class DependencyPostInitialize {

	@Bean
	public ServletRegistrationBean<HttpServlet> processorClientModuleTask() {
		ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
		servRegBean.setServlet(new FirestoreExecute());
		servRegBean.addUrlMappings("/run/firebase");
		servRegBean.setLoadOnStartup(1);
		return servRegBean;
	}
}
