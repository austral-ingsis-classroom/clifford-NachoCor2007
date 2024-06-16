package edu.austral.ingsis.clifford.communication;

public final class Failure implements ResultType {
  @Override
  public String getResultType() {
    return "Failure";
  }
}
