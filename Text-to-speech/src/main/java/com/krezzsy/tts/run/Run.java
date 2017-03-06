package com.krezzsy.tts.run;

import java.io.FileNotFoundException;

import javax.sound.sampled.AudioInputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.krezzsy.tts.util.AudioSynthesizer;
import com.krezzsy.tts.util.CLIHelper;
import com.krezzsy.tts.util.FileManager;

import marytts.exceptions.MaryConfigurationException;

/**
 * Class suggests to run Text-to-speech application
 * 
 * @author Gleb Melnichenko
 *
 */
public class Run {
	private static Logger logger = LogManager.getLogger(Run.class);
	static final String NAME = "txt2wav";
	static final String IN_OPT = "input";
	static final String OUT_OPT = "output";

	public static void main(String[] args) throws MaryConfigurationException {

		final CLIHelper cliHelper = new CLIHelper();
		final FileManager fileManager = new FileManager();
		final AudioSynthesizer synthesizer = new AudioSynthesizer();
		Options options = cliHelper.initOptions();
		CommandLine line = cliHelper.parseCL(options, args);
		String outputFileName = null;
		String inputText = null;
		AudioInputStream audio = null;
		try {
			outputFileName = fileManager.createOutput(line);
			inputText = fileManager.getInput(line);
			audio = synthesizer.synthesize(inputText);
		} catch (FileNotFoundException e) {
			logger.error("Can't get a resource file! Please, create input and output files!");
		}
		fileManager.writeAudio(audio, outputFileName);

	}
}
