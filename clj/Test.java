package clj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class Test {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		// StringTokenizer stringTokenizer = new
		// StringTokenizer("You are tokenizing a string");
		// System.out.println("The total no. of tokens  generated :  " +
		// stringTokenizer.countTokens());
		// while(stringTokenizer.hasMoreTokens()){
		// System.out.println(stringTokenizer.nextToken());
		// }

		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.clear();
//		bb.putInt(1);
		bb.put("liuwanzhen".getBytes());
		//bb.flip();
		//System.out.println(bb.getInt());
//		CharBuffer cb = bb.asCharBuffer();
//		cb.append("abc");
//		IntBuffer ib = bb.asIntBuffer();
//		ib.put(10);
//		ib.flip();
//		//bb.putInt(2);
//		bb.flip();
//		cb.flip();
		bb.flip();
//		System.out.println(new String(bb.));
//		System.out.println(cb.get());
//		System.out.println(ib.get());
		
		
		
//		byte[] buf = new byte[1024];
//		int len = bb.get(buf);
		
		FileInputStream fis = new FileInputStream("e:/temp/classpath.clj");
		FileChannel fc = fis.getChannel();
		ByteBuffer bbf = ByteBuffer.allocate(20);
		int len = fc.read(bbf);
		System.out.println(len);
		bbf.flip();
		while(bbf.hasRemaining()){
			Thread.sleep(1000);
			System.out.print(bbf.toString());
			System.out.println();
		}
		
	}
	
	public static int len(int i){
		return i < 10 ? 2 : 3 ;
	}
	
	public static void showPercent(int i){
		
		System.out.print(i + "%");
		if (i < 10) {
			loopPrintBack(2);
		} else {
			loopPrintBack(3);
		}
	}
	
	public static void loopPrintBack(int count){
		for (int i = 0; i < count; i++) {
			System.out.print("\b");
		}
	}
	
	public static void loopPrintNewLine(int count){
		for (int i = 0; i < count; i++) {
			System.out.print("\n");
		}
	}

}
