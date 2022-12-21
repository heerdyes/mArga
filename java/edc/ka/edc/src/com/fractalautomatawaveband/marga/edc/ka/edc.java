package com.fractalautomatawaveband.marga.edc.ka;

import static com.fractalautomatawaveband.marga.edc.ka.FS.loadfile;
import static com.fractalautomatawaveband.marga.edc.ka.U.cadr;
import static com.fractalautomatawaveband.marga.edc.ka.U.fmt;
import static com.fractalautomatawaveband.marga.edc.ka.U.rdln;
import static com.fractalautomatawaveband.marga.edc.ka.U.rpad;
import static com.fractalautomatawaveband.marga.edc.ka.U.lpad;
import static com.fractalautomatawaveband.marga.edc.ka.U.say;
import static com.fractalautomatawaveband.marga.edc.ka.U.isPrintable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class edc {
	static List<Integer> ibuf = new ArrayList<Integer>();
	static int COLWIDTH = 16;
	static int cursor = 0;

	static void header() {
		say("    ", "");
		for (int i = 0; i < COLWIDTH; i++) {
			char colnm = (char) ('A' + i);
			fmt("  %s ", colnm);
		}
		say("    ", "");
		for (int i = 0; i < COLWIDTH; i++) {
			char colnm = (char) ('A' + i);
			fmt("%c", colnm);
		}
		say();
	}

	static void colptr() {
		say("    ", "");
		for (int i = 0; i < COLWIDTH; i++) {
			fmt(i == cursor % COLWIDTH ? "  v " : "    ");
		}
		say("    ", "");
		for (int i = 0; i < COLWIDTH; i++) {
			fmt(i == cursor % COLWIDTH ? "v" : " ");
		}
	}

	static void bufcat() {
		int colctr = 0, rowctr = 0;
		StringBuffer nbuf = new StringBuffer();
		StringBuffer cbuf = new StringBuffer();
		header();
		colptr();
		char ptr;
		for (int x : ibuf) {
			nbuf.append(String.format("%03d ", x));
			if (isPrintable(x)) {
				cbuf.append((char) x);
			} else {
				cbuf.append(".");
			}
			if ((colctr + 1) % COLWIDTH == 0) {
				ptr = rowctr == cursor / COLWIDTH ? '>' : ' ';
				fmt("\n%02d%c %s    %s", rowctr, ptr, nbuf.toString(), cbuf.toString());
				nbuf.setLength(0);
				cbuf.setLength(0);
				rowctr++;
			}
			colctr++;
		}
		ptr = rowctr == cursor / COLWIDTH ? '>' : ' ';
		fmt("\n%02d%c %s    %s", rowctr, ptr, rpad(nbuf.toString(), COLWIDTH * 4), cbuf.toString());
		say();
	}

	static void help() {
		say("f <filename>    -> load file into buffer");
		say("q               -> quit");
		say("b               -> print buffer");
		say("x               -> clear buffer");
		say("c C04           -> set cursor");
	}

	static void clrbuf() {
		ibuf.clear();
		say("buf cleared!");
	}

	static void setcursor(String p) {
		int col = p.charAt(0) - 'A';
		int row = Integer.parseInt(p.substring(1));
		cursor = row * COLWIDTH + col;
	}

	static void cmdproc(String c, String p) throws IOException {
		if (c.equals("f")) {
			loadfile(p, ibuf);
		} else if (c.equals("b")) {
			bufcat();
		} else if (c.equals("help") || c.equals("?")) {
			help();
		} else if (c.equals("x")) {
			clrbuf();
		} else if (c.equals("c")) {
			setcursor(p);
			bufcat();
		}
	}

	static void shloop() throws IOException {
		for (;;) {
			String cli = rdln("> ");
			if (cli.equals("q")) {
				break;
			}
			String[] lr = cadr(cli);
			cmdproc(lr[0], lr[1]);
		}
	}

	public static void main(String... args) throws IOException {
		shloop();
		say("bye!");
	}
}
