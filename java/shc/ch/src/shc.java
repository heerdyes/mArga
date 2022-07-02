import java.io.Console;
import java.io.File;


public class shc {

	static Console c;
	static String prompt="GMkII";

	static void init() {
		System.out.println("initializing the x shell...");
		c=System.console();
		if (c==null) {
			throw new RuntimeException("[ERROR] no console available! mayday!");
		}
	}

	static void repl() {
		for (; ; ) {
			c.printf("%s-> ", prompt);
			String cli=c.readLine();
			if (cli.equals("q")) {
				break;
			}
			if (cli.equals("pwd")) {
				c.printf("%s\n", System.getProperty("user.dir"));
			}
			else if (cli.equals("ls")) {
				File pwd=new File(System.getProperty("user.dir"));
				String[] subdirs=pwd.list();
				for (String sd:subdirs) {
					c.printf("  %s\n", sd);
				}
			}
		}
	}

	static void cleanup() {
		c.printf("goodbye!\n");
	}

	public static void main(String[] args) {
		init();
		repl();
		cleanup();
	}
}
