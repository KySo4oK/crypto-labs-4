import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
//        generateAndWriteWithMD5(passwordGenerator);
        generateAndWriteWithArgon(passwordGenerator);
    }

    private static void generateAndWriteWithArgon(PasswordGenerator passwordGenerator) throws IOException {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 32);

        List<String> pbe = IntStream.range(0, 100_000)
                .boxed()
                .map(i -> argon2.hash(1, 64, 8, passwordGenerator.getPassword()))
                .collect(Collectors.toList());

        Files.write(Paths.get("strong.csv"), pbe);
    }

    private static void generateAndWriteWithMD5(PasswordGenerator passwordGenerator) throws IOException {
        List<String> md5 = IntStream.range(0, 100_000)
                .boxed()
                .map(i -> {
                    String password = passwordGenerator.getPassword();
                    try {
                        return DigestUtils.md5Hex(password);
                    } catch (Exception ignored) {
                        return "";
                    }
                })
                .collect(Collectors.toList());

        Files.write(Paths.get("weak.csv"), md5);
    }

}
