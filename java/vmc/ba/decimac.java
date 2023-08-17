import java.io.*;
import java.util.*;

public class decimac {

	// constants
	private static int MAX_MEM_LIM = 10000;
	private static int DATA_WIDTH = 6;
	private static int INST_WIDTH = 0;
	private static int OPCODE_WIDTH = DATA_WIDTH + INST_WIDTH;
	private static String LPAD_FMT = "%0" + DATA_WIDTH + "d";

	// register initialization
	private static char[] AC = new char[DATA_WIDTH];
	private static char[] IR = new char[OPCODE_WIDTH];
	private static char[] PC = new char[DATA_WIDTH];
	private static char[] SC = new char[DATA_WIDTH];
	private static char[] DR = new char[DATA_WIDTH];
	private static char[] RB = new char[DATA_WIDTH];
	private static char[] WB = new char[DATA_WIDTH];

	// global variables
	private static String file_name;
	private static boolean HALT_FLAG = false;

	// memory initialization
	private static char[][] MEMORY = new char[MAX_MEM_LIM][OPCODE_WIDTH];
	static {
		for (int i = 0; i < MAX_MEM_LIM; i++) {
			for (int j = 0; j < OPCODE_WIDTH; j++) {
				MEMORY[i][j] = '0';
			}
		}
		for (int i = 0; i < DATA_WIDTH; i++) {
			AC[i] = '0';
			IR[i] = '0';
			PC[i] = '0';
			SC[i] = '0';
			DR[i] = '0';
			RB[i] = '0';
			WB[i] = '0';
			IR[i] = '0';
		}
	}

	public static void main(String[] args) throws IOException, FileNotFoundException, NullPointerException {
		if(args.length == 0) {
			System.out.println("no file to load. aborting decimal machine...");
			System.exit(0);
		}
		
		file_name = args[0];

		burn_program_file2mem(file_name);
		//mem_dump_head();

		inst_cycle();

		// dump subset of machine state
		//reg_dump();
		//mem_dump_head();
	}

	// burn program into decimal machine memory
	private static void burn_program_file2mem(String file) throws IOException, FileNotFoundException, NullPointerException {
		if(file == null) {
			throw new NullPointerException("file_name is null");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		int counter = 0;
		for (; ; ) {
			String input = br.readLine();
			if(input == null) {
				break;
			}
			if(input.length() != OPCODE_WIDTH) {
				// ignore
				continue;
			}
			for (int i = 0; i < OPCODE_WIDTH; i++) {
				MEMORY[counter][i] = input.charAt(i);
			}
			counter++;
		}
		br.close();
	}

	// instruction cycle steps
	static void fetch() {
		// IR <- M[PC]
		//System.out.println("*** FETCH ***");
		//System.out.println("PC: [" + new String(PC) + "]");
		System.arraycopy(MEMORY[reg2num(PC)], 0, IR, 0, OPCODE_WIDTH);
		set_reg(SC, String.format(LPAD_FMT, 1).toCharArray());
	}

	static void process() {
		//System.out.println("*** PROCESS ***");
		// read opcode from IR
		if(IR[0] == '6' && IR[1] == '0') { jump(); }
		if(IR[0] == '0' && IR[1] == '9') { add(); }
		if(IR[0] == '5' && IR[1] == '5') { move(); }
		if(IR[0] == '8' && IR[1] == '9') { memory(); }
		if(IR[0] == '0' && IR[1] == '0') { nop(); }
		if(IR[0] == '7' && IR[1] == '0') { stop(); }
		if(IR[0] == '3' && IR[1] == '2') { load(); }
		if(IR[0] == '5' && IR[1] == '7') { loadwb(); }
		if(IR[0] == '5' && IR[1] == '2') { loadrb(); }
		if(IR[0] == '6' && IR[1] == '7') { print(); }
		if(IR[0] == '8' && IR[1] == '0') { rshift(); }
		if(IR[0] == '7' && IR[1] == '4') { lshift(); }
		if(IR[0] == '8' && IR[1] == '3') { crshift(); }
		if(IR[0] == '7' && IR[1] == '7') { clshift(); }
		if(IR[0] == '6' && IR[1] == '5') { jmpz(); }
	}

	static void shift() {
		//System.out.println("*** SHIFT ***");
		// PC <- PC + SC
		set_reg(PC, add_reg(PC, SC));
	}

	// instruction cycle
	static void inst_cycle() {
		while (!HALT_FLAG) {
			fetch();
			process();
			shift();
		}
	}

	// utilities
	static int reg2num(char[] reg) {
		return Integer.parseInt(new String(reg));
	}

	// register addition
	static char[] add_reg(char[] param1, char[] param2) {
		// sanity check: array size consistency
		if(param1.length != param2.length) {
			System.out.println("add_reg: array lengths differ. register addition aborted.");
		}
		int iresult = reg2num(param1) + reg2num(param2);
		iresult = iresult % MAX_MEM_LIM;
		return String.format(LPAD_FMT, iresult).toCharArray();
	}

	// register subtraction
	// param1 - param2
	static char[] subtract_reg(char[] param1, char[] param2) {
		if(param1.length != param2.length) {
			System.out.println("subtract_reg: array size inconsistent. register subtract failed.");
			return null;
		}
		int iresult = reg2num(param1) + MAX_MEM_LIM - reg2num(param2);
		iresult = iresult % MAX_MEM_LIM;
		return String.format(LPAD_FMT, iresult).toCharArray();
	}

	// assignment method for register
	static void set_reg(char[] dest, char[] src) {
		//System.out.println("|src| = " + src.length + ", |dest| = " + dest.length);
		if(dest.length != src.length) {
			System.out.println("set_reg: array lengths differ. register setting aborted.");
		}

		for (int i = 0; i < dest.length; i++) {
			dest[i] = src[i];
		}
	}

	// assignment method for memory
	static void set_mem(int addr, char[] data) {
		System.arraycopy(data, 0, MEMORY[addr], INST_WIDTH, DATA_WIDTH);
	}

	// simulates 'head' UNIX command for memory dump
	static void mem_dump_head(int rows) {
		System.out.println("-------- MEMORY DUMP BEGINS --------");
		for (int i = 0; i < ((rows == -1) ? MEMORY.length : ((rows > MEMORY.length) ? MEMORY.length : rows)); i++) {
			System.out.println("decimal: " + new String(MEMORY[i]));
		}
		System.out.println("-------- MEMORY DUMP ENDS --------");
	}

	static void mem_dump_head() {
		mem_dump_head(10);
	}

	// register dump
	static void reg_dump() {
		System.out.println("-------- REGISTER DUMP BEGINS --------");
		System.out.println("AC: [" + Arrays.toString(AC) + "]");
		System.out.println("IR: [" + Arrays.toString(IR) + "]");
		System.out.println("PC: [" + Arrays.toString(PC) + "]");
		System.out.println("SC: [" + Arrays.toString(SC) + "]");
		System.out.println("DR: [" + Arrays.toString(DR) + "]");
		System.out.println("-------- REGISTER DUMP ENDS --------");
	}


	/*                           ---------------                           */
	/*-------------------------- INSTRUCTION SET --------------------------*/
	/*                           ---------------                           */

	static void jump() {
		set_reg(SC, subtract_reg(Arrays.copyOfRange(IR, INST_WIDTH, IR.length), PC));
	}
	static void jmpz() {
		// to be implemented
	}

	static void add() {
		set_reg(AC, add_reg(AC, Arrays.copyOfRange(IR, INST_WIDTH, IR.length)));
	}
	static void move() {
		// not yet implemented
	}
	static void memory() {
		// not yet implemented
	}
	static void nop() {
		// do nothing
	}
	static void stop() {
		//System.out.println("ALERT: STOP instruction encountered. halting system.");
		HALT_FLAG = true;
	}
	static void load() {
		set_reg(AC, Arrays.copyOfRange(IR, INST_WIDTH, IR.length));
	}
	static void loadrb() {
		// not yet implemented
	}
	static void loadwb() {
		set_reg(WB, IR);
	}
	static void store() {
		set_mem(reg2num(Arrays.copyOfRange(IR, INST_WIDTH, IR.length)), AC);
	}
	static void print() {
		System.out.println("" + new String(Arrays.copyOfRange(WB, 2, WB.length)));
	}
	static void rshift() {
		for (int i = DATA_WIDTH - 1; i >= 1; i--) {
			AC[i] = AC[i - 1];
		}
		AC[0] = 0;
	}
	static void lshift() {
		for (int i = 0; i <= DATA_WIDTH - 2; i++) {
			AC[i] = AC[i + 1];
		}
		AC[DATA_WIDTH - 1] = 0;
	}
	static void crshift() {
		char tmp = AC[DATA_WIDTH - 1];
		rshift();
		AC[0] = tmp;
	}
	static void clshift() {
		char tmp = AC[0];
		lshift();
		AC[DATA_WIDTH - 1] = tmp;
	}
}
