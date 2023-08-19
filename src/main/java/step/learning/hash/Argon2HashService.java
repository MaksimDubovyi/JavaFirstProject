package step.learning.hash;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2HashService implements IHashservice {
    @Override
    public String GetHash(String text) {
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        String springBouncyHash = arg2SpringSecurity.encode(text);

        Boolean passwordsMatch = arg2SpringSecurity.matches(text, springBouncyHash);

        if (passwordsMatch) {
            return springBouncyHash;
        } else {
            return null;
        }
    }
}
