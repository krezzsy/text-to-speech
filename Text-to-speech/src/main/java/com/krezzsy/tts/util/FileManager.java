package com.krezzsy.tts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import marytts.util.data.audio.MaryAudioUtils;

/**
 * Class provides a set of functions with file system, such as read, write and
 * create.
 * 
 * @author Gleb Melnichenko
 *
 */
public final class FileManager {
	private static Logger logger = LogManager.getLogger(FileManager.class);
	private static final String OUT_OPT = "output";
	private static final String IN_OPT = "input";

	public String createOutput(CommandLine line) throws FileNotFoundException {
		String outputFileName = null;
		if (line.hasOption(OUT_OPT)) {
			outputFileName = line.getOptionValue(OUT_OPT);
			if (!FilenameUtils.getExtension(outputFileName).equals("wav")) {
				outputFileName += ".wav";
			}
		} else {
			logger.error("Can't get the output file!");
			throw new FileNotFoundException();
		}
		return outputFileName;
	}

	public String getInput(CommandLine line) {
		String inputText = null;
		if (line.hasOption(IN_OPT)) {
			String inputFileName = line.getOptionValue(IN_OPT);
			File file = new File(inputFileName);
			try {
				inputText = FileUtils.readFileToString(file);
			} catch (IOException e) {
				logger.error("Could not read from file " + inputFileName + ": " + e.getMessage());
			}
		} else {
			try {
				inputText = line.getArgList().get(0);
			} catch (IndexOutOfBoundsException e) {
				logger.error("Reading file error!");
			}
		}
		if (inputText == null) {
			logger.error("Please provide an input text.");
		}
		return inputText;
	}

	public void writeAudio(AudioInputStream audio, String outputFileName) {
		double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
		try {
			MaryAudioUtils.writeWavFile(samples, outputFileName, audio.getFormat());
			logger.info("Output written to " + outputFileName);
		} catch (IOException e) {
			logger.error("Could not write to file: " + outputFileName + "\n" + e.getMessage());
		}
	}
}
