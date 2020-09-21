package com.codurance;

public abstract class PaymentGateway {
  abstract void processPayment(PaymentDetails paymentDetails);
}
