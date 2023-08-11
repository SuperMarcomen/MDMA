package it.marcodemartino.gitfromwish.encryption;

import it.marcodemartino.gitfromwish.logger.Logger;
import it.marcodemartino.gitfromwish.utils.HexConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Hashing implements Hashing {

    @Override
    public String hash(byte[] content) {
        String sha1 = "";
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(content);
            sha1 = HexConverter.encodeHexString(msdDigest.digest());
        } catch (NoSuchAlgorithmException exception) {
            Logger.error("Hashing algorithm SHA-1 not found!");
        }
        return sha1;
    }



}
