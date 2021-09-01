package br.com.esparda.commons;

public class EspardaStringBuilder {

	private final StringBuilder sb;

	public EspardaStringBuilder() {
		sb = new StringBuilder();
	}

	public EspardaStringBuilder(String str) {
		sb = new StringBuilder(str);
	}

	public void append(String str) {
		sb.append(str);
	}

	public void append(String... array) {
		if (Util.isNullOrEmpty(array)) {
			return;
		}
		for (String line : array) {
			sb.append(line);
		}
	}

	public void appendLine(String str) {
		sb.append(str);
		sb.append(System.lineSeparator());
	}

	public void appendLine(String... array) {
		if (Util.isNullOrEmpty(array)) {
			return;
		}
		for (String line : array) {
			sb.append(line);
		}
		sb.append(System.lineSeparator());
	}

	public void appendLines(String... array) {
		if (Util.isNullOrEmpty(array)) {
			return;
		}
		for (String line : array) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}
	}

	public void removeLastChar() {
		sb.delete(sb.length(), sb.length());
	}

	@Override
	public String toString() {
		return sb.toString();
	}

}
