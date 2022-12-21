package com.fractalautomatawaveband.marga.edc.ka;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.fractalautomatawaveband.marga.edc.ka.U.*;

public class FS {
	static void loadfile(String fn, List<Integer> buf) throws IOException {
		buf.clear();
		File f = new File(fn);
		try (FileReader fr = new FileReader(f)) {
			for (;;) {
				int x = fr.read();
				if (x == -1)
					break;
				buf.add(x);
			}
		} catch (IOException e) {
			say(e.getMessage());
		}
	}
}
