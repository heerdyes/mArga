package ed;

interface Buf{
  void append(char ch);
  void append(String s);
  void clear();
  void init(String s);
  String str();
  void insert(int pos,String s);
  void insert(int pos,char c);
}

