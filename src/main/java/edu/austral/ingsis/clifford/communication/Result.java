package edu.austral.ingsis.clifford.communication;

import edu.austral.ingsis.clifford.communication.commtype.ResultType;

public record Result<E>(ResultType resultType, E value, String message) {
}
