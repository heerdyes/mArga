public class U {

	public static String[] eattillspace(String x) {
		int splitpt=-1;
		for (int i=0; i<x.length(); i++) {
			if (x.charAt(i)==' ') {
				splitpt=i;
				break;
			}
		}
		if (splitpt==-1) {
			return new String[] {x, ""};
		}
		else {
			return new String[] {x.substring(0, splitpt), x.substring(splitpt+1, x.length())};
		}
	}

	static String[] rmlast(String[] x) {
		String[] v=new String [x.length-1];
		for (int i=0; i<v.length; i++) {
			v[i]=x[i];
		}
		return v;
	}

	static String join(String[] x, String delim) {
		if (x.length==0) {
			return null;
		}
		if (x.length==1) {
			return x[0];
		}
		StringBuffer sb=new StringBuffer();
		int xn=x.length;
		for (int i=0; i<xn-1; i++) {
			sb.append(x[i]);
			sb.append(delim);
		}
		sb.append(x[xn-1]);
		return sb.toString();
	}
}
