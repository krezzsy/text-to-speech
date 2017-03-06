package com.krezzsy.tts.util;

import java.io.FileNotFoundException;

import javax.sound.sampled.AudioInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

/**
 * Class provides a synthesize from text to audio(.wav file) function
 * 
 * @author Gleb Melnichenko
 *
 */
public final class AudioSynthesizer {
	private static Logger logger = LogManager.getLogger(AudioSynthesizer.class);

	public AudioInputStream synthesize(String inputText) throws FileNotFoundException {
		LocalMaryInterface mary = null;
		try {
			mary = new LocalMaryInterface();
		} catch (MaryConfigurationException e) {
			logger.error("Could not initialize MaryTTS interface: " + e.getMessage());
		}

		AudioInputStream audio = null;
		try {
			audio = mary.generateAudio(inputText);
		} catch (SynthesisException e) {
			logger.error("Could not generate audio: " + e.getMessage());
			throw new FileNotFoundException();
		}
		return audio;
	}
}
