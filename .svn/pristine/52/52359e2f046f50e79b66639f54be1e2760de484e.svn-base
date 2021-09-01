package br.com.esparda.commons;

import java.util.List;

import br.com.esparda.commons.domain.IEntidade;
import br.com.esparda.commons.exception.EspardaRuntimeException;
import br.com.esparda.commons.meta.EntidadeAnnotation;
import tools.devnull.trugger.scan.ClassScan;

public class EntidadeUtil {

	public static final String ATIRUBTO_CHAVE = "chave";

	public static final IEntidade<?> create(String className) {
		try {
			Class<?> clazz = getClass(className);
			return (IEntidade<?>) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Class<IEntidade<?>> getClass(String className) {
		if (Util.isNullOrEmpty(className)) {
			LogUtil.warn("className == null");
			throw new EspardaRuntimeException("className == null");
		}
		if (className.indexOf('.') < 0) {
			List<Class> classes = ClassScan.scan().classes().deep().filter(clazz -> compare(clazz, className))
					.in("br.com.esparda");
			return classes.get(0);
		}
		try {
			Class<?> clazz = Class.forName(className);
			return (Class<IEntidade<?>>) clazz;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private static boolean compare(Class<?> clazz, String className) {
		if (!clazz.isAnnotationPresent(EntidadeAnnotation.class)) {
			return false;
		}
		if (!clazz.getSimpleName().equalsIgnoreCase(className)) {
			return false;
		}
		return true;
	}

}
