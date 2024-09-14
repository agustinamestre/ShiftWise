package com.agustinamestre.ShiftWiseBackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShiftWiseBackendApplication {
	static Logger logger = LogManager.getLogger(ShiftWiseBackendApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(ShiftWiseBackendApplication.class, args);

		logger.info("  ____   _  __");
		logger.info(" / __ \\ | |/ /");
		logger.info("| |  | || ' / ");
		logger.info("| |  | ||  <  ");
		logger.info("| |__| || . \\ ");
		logger.info(" \\____(_)_|\\_\\");


	}

}
