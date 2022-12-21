package com.fractalautomatawaveband.marga.edc.ka;

import java.io.IOException;
import static com.fractalautomatawaveband.marga.edc.ka.FS.*;
import static com.fractalautomatawaveband.marga.edc.ka.U.*;

public class edc {
	static StringBuffer buf = new StringBuffer();

	static void bufcat() {
		int lnctr = 0;
		fmt("%02d: ", lnctr);
		for (int i = 0; i < buf.length(); i++) {
			char c = buf.charAt(i);
			if (c == '\n') {
				lnctr++;
				fmt("\n%02d: ", lnctr);
			} else {
				say(c, "");
			}
		}
		say();
	}

	static void help() {
		say("f <filename>    -> load file into buffer");
		say("q               -> quit");
		say("b               -> print buffer");
	}

	static void cmdproc(String c, String p) throws IOException {
		if (c.equals("f")) {
			loadfile(p, buf);
		} else if (c.equals("b")) {
			bufcat();
		} else if (c.equals("help") || c.equals("?")) {
			help();
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
