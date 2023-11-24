package com.example.commonservice.security;

import org.springframework.stereotype.Service;

import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class PasswordGenerator {
    public String generatePassword() {
        Random random = new Random();
        IntStream stream = random.ints(48, 123);
        PrimitiveIterator.OfInt iterator = stream.iterator();
        StringBuilder password = new StringBuilder();
        while (password.length() < 8) {
            int i = iterator.nextInt();
            if ((i >= '1' && i <= '9')
                    || (i >= 'A' && i <= 'Z' && i != 'I' && i != 'O')
                    || (i >= 'a' && i <= 'z' && i != 'l' && i != 'o')) {
                password.append((char) i);
            }
        }
        return password.toString();
    }
}
