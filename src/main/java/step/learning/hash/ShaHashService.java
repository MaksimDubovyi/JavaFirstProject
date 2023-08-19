package step.learning.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaHashService implements  IHashservice{
    @Override
    public String GetHash(String text) {
        String sha1Hex = DigestUtils.sha1Hex(text);
        return sha1Hex;
    }
}
