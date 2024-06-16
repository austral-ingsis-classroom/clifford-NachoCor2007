package edu.austral.ingsis.clifford.communication;

public record Result<E>(ResultType resultType, E value, String message) {
}
