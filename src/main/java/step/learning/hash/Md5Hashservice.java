package step.learning.hash;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5Hashservice implements IHashservice{
    public String GetHash(String text)
    {
        String md5Hex = DigestUtils.md5Hex(text);
        return md5Hex;
    }
}
