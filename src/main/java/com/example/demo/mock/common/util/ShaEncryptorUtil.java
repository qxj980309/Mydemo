package com.example.demo.mock.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static org.apache.commons.codec.digest.DigestUtils.getSha1Digest;
import static org.apache.commons.codec.digest.DigestUtils.getSha256Digest;

public final class ShaEncryptorUtil {
    private static final char[] DIGITS_UPPER = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static String sha256(final String msg) {
        return msg == null ? null : encodeHexString(getSha256Digest(),msg);
    }

    public static String sha1(final String msg) {
        return msg == null ? null : encodeHexString(getSha1Digest(),msg);
    }

    private static String encodeHexString(final MessageDigest messageDigest, String msg) {
        final byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        final byte[] digest = messageDigest.digest(bytes);
        final char[] hex = encodeHex(digest,DIGITS_UPPER);
        return new String(hex);
    }

    private static char[] encodeHex(final byte[] digest, final char[] digitsUpper) {
        final int len = digest.length;
        final char[] out = new char[len<<1];
        for(int i = 0 , j = 0; i< len;i++){
            out[j++] = digitsUpper[(0xF0 & digest[i])>>>4];
            out[j++] = digitsUpper[0xF0 & digest[i]];
        }
        return out;
    }
}
