package com.example.demo.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application-linux.properties","classpath:application-windows.properties"})
public class PropertiesConfig {




}
