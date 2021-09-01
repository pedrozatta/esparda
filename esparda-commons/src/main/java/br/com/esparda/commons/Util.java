package br.com.esparda.commons;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T newInstance(Class<T> clazz) {
		try {
			if (clazz.equals(List.class)) {
				return (T) new ArrayList();
			}

			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}

	public static boolean isNullOrEmpty(Object object) {
		return object == null;
	}

	public static boolean isNullOrEmpty(String object) {
		return object == null || object.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Collection coll) {
		return coll == null || coll.isEmpty();
	}

	public static boolean isNullOrEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static String getServerName() {
		try {
			// String server = InetAddress.getLocalHost().getHostName();
			// String ip = InetAddress.getLocalHost().getHostAddress();
			String canonical = InetAddress.getLocalHost().getCanonicalHostName();
			return canonical;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

}
