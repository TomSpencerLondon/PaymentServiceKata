package com.codurance;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentServiceShould {

  @Test
  void throw_an_exception_when_user_does_not_exist() {
    PaymentService paymentService = new PaymentService();
    User blankUser = new UserStub(false);
    PaymentDetails paymentDetails = new PaymentDetails();
    assertThrows(NoUserException.class, () -> {
      paymentService.processPayment(blankUser, paymentDetails);
    });
  }

  private class UserStub implements User {
    private boolean exists;

    public UserStub(boolean exists) {
      this.exists = exists;
    }
  }
}
