package step.learning.hash;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha512HashService implements  IHashservice{
    @Override
    public String GetHash(String text) {
        String sha512Hex = DigestUtils.sha512Hex(text);
        return sha512Hex;
    }
}
