package com.fractalautomatawaveband.marga.edc.ka;

import java.io.IOException;

public class U {
	public static String rdln() throws IOException {
		StringBuffer sb = new StringBuffer();
		for (;;) {
			int d = System.in.read();
			char c = (char) d;
			if (d == 13) { // enter := <13><10>
				System.in.read(); // eat <10>
				break;
			}
			// only tested on windows, feared non-portable
			sb.append(c);
		}
		return sb.toString();
	}

	public static String rdln(String prompt) throws IOException {
		System.out.print(prompt);
		return rdln();
	}

	public static void inspect(String s) {
		System.out.printf("[inspect] ");
		for (int i = 0; i < s.length(); i++) {
			int code = s.charAt(i);
			System.out.printf("%d ", code);
		}
		System.out.println();
	}

	public static String[] cadr(String s) {
		String[] rs = new String[] { "", "" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			if (curr == ' ') {
				rs[1] = s.substring(i + 1);
				break;
			}
			sb.append(curr);
		}
		rs[0] = sb.toString();
		return rs;
	}

	public static void say(String msg) {
		System.out.println(msg);
	}

	public static void say(String msg, String suffix) {
		System.out.printf("%s%s", msg, suffix);
	}

	public static void say() {
		System.out.println();
	}

	public static void fmt(String f, Object... args) {
		System.out.printf(f, args);
	}

	public static void say(char c, String end) {
		System.out.printf("%c%s", c, end);
	}
}
