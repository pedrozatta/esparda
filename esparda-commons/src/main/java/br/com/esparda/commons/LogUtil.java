package br.com.esparda.commons;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {

	public static void debug(String message) {
		System.out.print("Debug: ");
		System.out.println(message);
	}

	public static void debug(Logger logger, String pattern, Object... args) {
		logger.log(Level.INFO, pattern, args);
	}

	public static void debug(String pattern, Object... args) {
		System.out.print("Debug: ");
		System.out.println(String.format(pattern, args));
	}

	public static void warn(String message) {
		System.out.print("Warn: ");
		System.out.println(message);
	}

	public static void warn(String pattern, Object... args) {
		System.out.print("Warn: ");
		System.out.println(String.format(pattern, args));
	}

	public static void error(String message) {
		System.out.print("Erro: ");
		System.out.println(message);
	}

	public static void error(String pattern, Object... args) {
		System.out.print("Erro: ");
		System.out.println(String.format(pattern, args));
	}

}
