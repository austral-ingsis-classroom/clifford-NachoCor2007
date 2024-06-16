package edu.austral.ingsis.clifford.communication.commtype;

public sealed interface ResultType permits Success, Failure {
  String getResultType();
}
