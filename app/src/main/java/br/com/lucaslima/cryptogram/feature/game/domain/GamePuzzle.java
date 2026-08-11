package br.com.lucaslima.cryptogram.feature.game.domain;

import java.util.HashSet;
import java.util.Set;

public final class GamePuzzle {

    private final char[] cipher;
    private final char[] solution;
    private final Set<Character> revealedCiphers;

    public GamePuzzle(String cipher, String solution, char... revealedCiphers) {
        if (cipher == null || solution == null) {
            throw new IllegalArgumentException("cipher and solution must not be null");
        }
        if (cipher.length() != solution.length()) {
            throw new IllegalArgumentException("cipher and solution must have the same length");
        }
        this.cipher = cipher.toUpperCase().toCharArray();
        this.solution = solution.toUpperCase().toCharArray();
        this.revealedCiphers = new HashSet<>();
        for (char c : revealedCiphers) {
            this.revealedCiphers.add(Character.toUpperCase(c));
        }
    }

    public int length() {
        return cipher.length;
    }

    public char cipherAt(int i) {
        return cipher[i];
    }

    public char solutionAt(int i) {
        return solution[i];
    }

    public boolean isSpace(int i) {
        return cipher[i] == ' ';
    }

    public boolean isRevealedCipher(char c) {
        return revealedCiphers.contains(Character.toUpperCase(c));
    }
}
