import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {

    List<String> top100000 = Files.lines(Paths.get("top100000.txt"))
            .collect(Collectors.toList());

    List<String> top100 = Files.lines(Paths.get("top100.txt"))
            .collect(Collectors.toList());

    public PasswordGenerator() throws IOException {
    }

    String getPassword() {
        long l = getRandomNumber();

        if (l <= 10) {
            return getFromTop100();
        } else if (l <= 85) {
            return getFromTop100000();
        }
        return getRandomPassword();
    }

    private long getRandomNumber() {
        SecureRandom random = new SecureRandom();
        while (random.nextInt() > 0.6) {
            random.nextInt();
        }
        return random.nextInt() % 100;
    }

    private String getRandomPassword() {
        return RandomStringUtils.randomAlphabetic(6) + RandomStringUtils.randomNumeric(4);
    }

    private String getFromTop100000() {
        int i1 = (int) (Math.random() * top100000.size());
        return top100000.get(i1);
    }

    private String getFromTop100() {
        int i1 = (int) (Math.random() * top100.size());
        return top100.get(i1);
    }


}
