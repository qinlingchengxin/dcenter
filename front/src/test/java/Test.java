import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * User: LiWenC
 * Date: 17-11-24
 */
public class Test {
    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("e:/test.sql", false));
        long time = System.currentTimeMillis();
        for (int i = 2000; i < 3000; i++) {
            String s = "INSERT INTO T_1 ( SYS__ID, ID, NAME, SYS__PLATFORM_CODE, SEX, AGE, SYS__CREATE_TIME ) VALUES ('" + UUID.randomUUID().toString() + "','" + UUID.randomUUID().toString() + "','name:" + i + "','48b4c52a',1," + i + "," + time + ");";
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }
}
