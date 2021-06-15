package org.onecell.common.crypto.encode;



import org.onecell.common.crypto.BCrypt;

public class BcryptPasswordEncoder implements  PasswordEncoder {
    final int ROUNDS = 12;


    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw( rawPassword.toString(),BCrypt.gensalt(ROUNDS));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return BCrypt.checkpw(rawPassword.toString(),encodedPassword);
    }
}
