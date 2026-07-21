package br.com.lucaslima.cryptogram.feature.home.domain;

public record Puzzle(
        String id,
        String encryptedText,
        String hint,
        String solution
) {}
