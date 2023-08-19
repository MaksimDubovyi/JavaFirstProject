package step.learning.hash;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha256HashService implements  IHashservice{
    @Override
    public String GetHash(String text) {
        String sha256Hex = DigestUtils.sha256Hex(text);
        return sha256Hex;
    }
}