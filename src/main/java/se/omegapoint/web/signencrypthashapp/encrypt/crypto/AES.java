package se.omegapoint.web.signencrypthashapp.encrypt.crypto;

import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class AES {

    private final List<String> types = Arrays.asList("ECB", "CBC", "CFB", "OFB");
    private final List<String> paddings = Arrays.asList("NoPadding", "PKCS5Padding");

    boolean correct = false;

    public AES(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        correct = Utils.encrypt(encryptVO, getSecretKey(encryptVO), 16, types, paddings);
    }

    public boolean isCorrect() {
        return correct;
    }

    private Key getSecretKey(EncryptVO encryptVO) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int keyLength = Integer.parseInt(encryptVO.getKeyLength());
        Key secretKey =  null;

        MessageDigest sha = null;
        byte[] key = encryptVO.getSecret().getBytes("UTF-8");
        System.out.println("keyLength: "+keyLength);
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLength/8);
        System.out.println("SecretKey: "+Utils.bytesToHex(key));
        System.out.println("SecretKey (base64): "+Utils.base64String(Utils.bytesToHex(key)));
        secretKey = new SecretKeySpec(key, "AES");

        return secretKey;

    }

}
