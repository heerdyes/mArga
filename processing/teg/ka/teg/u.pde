import java.util.*;

color blk = color(0);
color trn = color(23, 202, 232);
color grn = color(0, 240, 0);
color erd = color(240, 64, 0);

void d(String s) {
  System.out.println(s);
}

void d() {
  System.out.println();
}

void d(String[] ss) {
  for (String s : ss) {
    System.out.println(s);
  }
}

void d(List<String> ss) {
  for (String s : ss) {
    System.out.println(s);
  }
}
