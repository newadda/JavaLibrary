package org.onecell.common.crypto.encode;

public interface PasswordEncoder {
    /**
     * 원본 패스워드를 인코딩한다.
     */
    String encode(CharSequence rawPassword);

    /**
     * 패스워드 비교
     *
     * @param rawPassword 인코딩하지 않은 원본 패스워드
     * @param encodedPassword 인코딩된 패스워드
     * @return true 원본 패스워드를 인코딩한 값과 인코딩된 값이 값을 경우
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
