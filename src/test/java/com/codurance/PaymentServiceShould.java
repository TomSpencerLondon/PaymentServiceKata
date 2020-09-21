package com.codurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentServiceShould {

  @Test
  void throw_an_exception_when_user_does_not_exist() {
    PaymentGateway paymentGatewayMock = new PaymentGatewayMock();
    PaymentService paymentService = new PaymentService(paymentGatewayMock);
    User blankUser = new UserStub(false);
    PaymentDetails paymentDetails = new PaymentDetails();
    assertThrows(NoUserException.class, () -> {
      paymentService.processPayment(blankUser, paymentDetails);
    });
  }

  @Test
  void sends_to_payment_gateway_if_user_exists() throws NoUserException {
    PaymentGatewayMock paymentGatewayMock = new PaymentGatewayMock();
    PaymentService paymentService = new PaymentService(paymentGatewayMock);
    User existingUser = new UserStub(true);

    PaymentDetails paymentDetails = new PaymentDetails();
    paymentService.processPayment(existingUser, paymentDetails);
    paymentGatewayMock.expectPaymentDetails(paymentDetails);
    paymentGatewayMock.verify();
  }

  private class UserStub implements User {
    private boolean exists;

    public UserStub(boolean exists) {
      this.exists = exists;
    }

    public boolean exists() {
      return exists;
    }
  }

  private class PaymentGatewayMock extends PaymentGateway {
    private PaymentDetails expectedPaymentDetails;
    private PaymentDetails receivedPaymentDetails;

    @Override
    public void processPayment(PaymentDetails paymentDetails) {
      receivedPaymentDetails = paymentDetails;
    }

    public void expectPaymentDetails(PaymentDetails paymentDetails){
      expectedPaymentDetails = paymentDetails;
    }

    public void verify() {
      assertEquals(expectedPaymentDetails, receivedPaymentDetails);
    }
  }
}
