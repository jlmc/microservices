package io.github.jlmc.chassis.hashing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MD5EncryptorTest {

    @Test
    void encrypt() {
        MD5Encryptor md5Encryptor = new MD5Encryptor();
        String hash = md5Encryptor.hash("hello word");

        Assertions.assertEquals("13574ef0d58b50fab38ec841efe39df4", hash);
    }
}