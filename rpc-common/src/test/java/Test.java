
public class Test {

    @org.junit.Test
    public void testA() {
        int s = 258;
        System.out.println((byte)s);
        System.out.println(s >>> 8);
    }

    @org.junit.Test
    public void testB() {
        byte[] buffer = new byte[32];
        buffer[0] = (byte) 123;
        System.out.println(buffer[0]);
    }

    @org.junit.Test
    public void testC() {
        int s = Integer.MAX_VALUE - 1;
        byte[] buffer = new byte[4];
        buffer[3] = (byte) s;
        buffer[2] = (byte) (s >>> 8);
        buffer[1] = (byte) (s >>> 16);
        buffer[0] = (byte) (s >>> 24);

        int result = ((buffer[0] & 0xff) << 24) +
                ((buffer[1] & 0xff) << 16) +
                ((buffer[2] & 0xff) << 8) +
                ((buffer[3] & 0xff));
        System.out.println(s);
        System.out.println(result);
        //assert s == result;
    }

    @org.junit.Test
    public void testD() {
        String s = "hello";
        byte[] buffer = s.getBytes();
        for (byte b : buffer) {
            System.out.println((char) b);
        }
    }

    @org.junit.Test
    public void testE() {
        //53 65 72 69 61 6c 54 65 73 74
        System.out.println((char) (0x53) + " " + (char) (0x65));
    }

}
