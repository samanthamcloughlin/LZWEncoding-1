import java.io.IOException;

public class LZWTester {
	public static void main(String[] args) throws IOException {
		LSWEncoding encoder = new LSWEncoding();
		encoder.encoding("file2.txt");
		
	}
}

/*
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 * HHHHHHHHHHHHHH        IIIIII
 * HHHHHHHHHHHHHH        IIIIII
 * HHHHHHHHHHHHHH        IIIIII
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 * HHHHH    HHHHH   IIIIIIIIIIIIIIII
 */

