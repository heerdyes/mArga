import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class shc {

	static Console c;
	static String prompt="GMkII";
	static String cd;

	static void init() {
		c=System.console();
		if (c==null) {
			throw new RuntimeException("[ERROR] no console available! mayday!");
		}
		cd=System.getProperty("user.dir");
	}

	static void cmdexec(String cli) {
		String[] cadr=U.eattillspace(cli);
		String cmd=cadr[0];
		String cdrs=cadr[1];
		if (cmd.equals("pwd")) {
			c.printf("%s\n", cd);
		}
		else if (cmd.equals("ls")) {
			File pwd=new File(cd);
			c.printf("%s", FS.ls(pwd));
		}
		else if (cmd.equals("cat")) {
			String[] params=U.eattillspace(cdrs);
			String arg0=params[0];
			File tf=new File(String.format("%s%s%s", cd, File.separator, arg0));
			c.printf("%s", FS.cat(tf));
		}
		else if (cmd.equals("touch")) {
			String[] params=U.eattillspace(cdrs);
			String arg0=params[0];
			File tf=new File(String.format("%s%s%s", cd, File.separator, arg0));
			c.printf("%s", FS.touch(tf));
		}
		else if (cmd.equals("cd")) {
			String[] params=U.eattillspace(cdrs);
			String arg0=params[0];
			if (arg0.equals("..")) {
				cd=U.join(U.rmlast(cd.split(File.separator)), File.separator);
			}
			else if (arg0.equals(".")) {
				// do nothing
			}
			else if (arg0.startsWith(File.separator)) {
				cd=arg0;
			}
			else {
				cd=String.format("%s%s%s", cd, File.separator, arg0);
			}
		}
	}

	static boolean sayonara(String cli) {
		return cli.equals("q")||cli.equals("quit")||cli.equals("bye")||cli.equals("sayonara")||cli.equals("exit")||cli.equals("astalavista");
	}

	static void repl() {
		for (; ; ) {
			c.printf("%s-> ", prompt);
			String cli=c.readLine();
			if (sayonara(cli)) {
				break;
			}
			cmdexec(cli);
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
