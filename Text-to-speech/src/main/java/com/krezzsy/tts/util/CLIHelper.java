package com.krezzsy.tts.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Class sets all the necessary arguments for work of Text-to-speech application
 * 
 * @author Gleb Melnichenko
 *
 */
public final class CLIHelper {
	private static Logger logger = LogManager.getLogger(CLIHelper.class);
	private static final String IN_OPT = "input";
	private static final String OUT_OPT = "output";
	private static final String FILE = "FILE";
	private static final String OUT = "o";
	private static final String IN = "o";

	public Options initOptions() {
		Options options = new Options();
		Option outputOption = Option.builder(OUT).longOpt(OUT_OPT).hasArg().argName(FILE).desc("Write output to FILE").required().build();
		Option inputOption = Option.builder(IN).longOpt(IN_OPT).hasArg().argName(FILE).desc("Read input from FILE\n(otherwise, read from command line argument)").build();
		options.addOption(outputOption);
		options.addOption(inputOption);
		return options;
	}

	public CommandLine parseCL(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			logger.error("Error occured while parsing!" + e.getMessage());
		}
		return line;
	}

}
