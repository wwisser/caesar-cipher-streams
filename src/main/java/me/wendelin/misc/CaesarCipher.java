package me.wendelin.misc;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CaesarCipher {

    private static final int ALPHABET_SIZE = 26;

    public static void main(String... args) {
        if (args.length < 3) {
            System.out.println("Usage: <encode|decode> <offset> <message...>");
            return;
        }

        String inputMode = args[0].toLowerCase();

        if (!inputMode.equals("encode") && !inputMode.equals("decode")) {
            throw new IllegalArgumentException("mode must be either encode or decode");
        }

        boolean encode = inputMode.equals("encode");
        int offset = Integer.parseInt(args[1]);
        char[] charset = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).toCharArray();

        System.out.println(encode ? CaesarCipher.encode(charset, offset) : CaesarCipher.decode(charset, offset));
    }

    private static String encode(char[] charset, int offset) {
        return IntStream.range(0, charset.length)
            .mapToObj(i -> charset[i])
            .map(character -> {
                if (!Character.isAlphabetic(character)) {
                    return character;
                }

                char startOffset = Character.isUpperCase(character) ? 'A' : 'a';

                return (char) (startOffset + (character - startOffset + offset) % CaesarCipher.ALPHABET_SIZE);
            })
            .collect(
                StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append
            ).toString();
    }

    private static String decode(char[] charset, int offset) {
        return CaesarCipher.encode(charset, CaesarCipher.ALPHABET_SIZE - offset);
    }

}
