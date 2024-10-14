package egovframework.utils;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class Encryption {

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     *
     * @param password 암호화될 패스워드
     * @param id       salt로 사용될 사용자 ID 지정
     * @return String
     * @throws Exception
     */
    public static String encryptPassword(String password, String id) throws Exception {

        if (password.isEmpty() || id.isEmpty()) {
            return "";
        }

        byte[] hashValue = null; // 해쉬값

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.reset();
        md.update(id.getBytes());

        hashValue = md.digest(password.getBytes());

        return new String(Base64.encodeBase64(hashValue));
    }

    /**
     * 무작위 숫자 + 알파 랜덤코드
     *
     * @param pwLength 난수 길이
     * @return String
     */
    public static String createRandomCode(int pwLength) {

        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";

        // 랜덤 문자열 생성 함수
        Random random = new Random();
        StringBuilder sb = new StringBuilder(pwLength);
        for (int i = 0; i < pwLength; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();

    }

}