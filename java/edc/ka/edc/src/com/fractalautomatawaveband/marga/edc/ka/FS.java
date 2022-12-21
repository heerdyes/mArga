package com.fractalautomatawaveband.marga.edc.ka;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static com.fractalautomatawaveband.marga.edc.ka.U.*;

public class FS {
	static void loadfile(String fn, StringBuffer buf) throws IOException {
		File f = new File(fn);
		try (FileReader fr = new FileReader(f)) {
			for (;;) {
				int x = fr.read();
				if (x == -1)
					break;
				char c = (char) x;
				buf.append(c);
			}
		} catch (IOException e) {
			say(e.getMessage());
		}
	}
}
