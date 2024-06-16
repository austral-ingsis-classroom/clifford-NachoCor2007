package edu.austral.ingsis.clifford.communication;

public sealed interface ResultType permits Success, Failure {
  String getResultType();
}
