package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.PaymentIntentStatus;
import com.payment.gateway.exception.InvalidPaymentIntentTransitionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentIntentTest {

    @Test
    void shouldStartInRequiresPaymentMethod() {
        PaymentIntent intent = new PaymentIntent();

        assertEquals(PaymentIntentStatus.REQUIRES_PAYMENT_METHOD, intent.getStatus());
    }

    @Test
    void shouldAllowOnlyConfiguredTransitionsForEveryStatus() {
        PaymentIntentStatus[] statuses = PaymentIntentStatus.values();

        for (PaymentIntentStatus from : statuses) {
            for (PaymentIntentStatus to : statuses) {
                PaymentIntent intent = intentIn(from);
                String transition = "Transition from " + from + " to " + to;

                if (isAllowed(from, to)) {
                    intent.transitionTo(to);
                    assertEquals(to, intent.getStatus(), transition);
                } else {
                    assertThrows(
                            InvalidPaymentIntentTransitionException.class,
                            () -> intent.transitionTo(to),
                            transition
                    );
                    assertEquals(from, intent.getStatus(),
                            "Rejected transition must leave status unchanged: " + transition);
                }
            }
        }
    }

    private static PaymentIntent intentIn(PaymentIntentStatus status) {
        PaymentIntent intent = new PaymentIntent();
        switch (status) {
            case REQUIRES_PAYMENT_METHOD -> { }
            case REQUIRES_CONFIRMATION ->
                    intent.transitionTo(PaymentIntentStatus.REQUIRES_CONFIRMATION);
            case PROCESSING -> {
                intent.transitionTo(PaymentIntentStatus.REQUIRES_CONFIRMATION);
                intent.transitionTo(PaymentIntentStatus.PROCESSING);
            }
            case SUCCEEDED, FAILED -> {
                intent.transitionTo(PaymentIntentStatus.REQUIRES_CONFIRMATION);
                intent.transitionTo(PaymentIntentStatus.PROCESSING);
                intent.transitionTo(status);
            }
        }
        return intent;
    }

    private static boolean isAllowed(PaymentIntentStatus from, PaymentIntentStatus to) {
        return switch (from) {
            case REQUIRES_PAYMENT_METHOD -> to == PaymentIntentStatus.REQUIRES_CONFIRMATION;
            case REQUIRES_CONFIRMATION -> to == PaymentIntentStatus.PROCESSING;
            case PROCESSING -> to == PaymentIntentStatus.SUCCEEDED
                    || to == PaymentIntentStatus.FAILED;
            case SUCCEEDED, FAILED -> false;
        };
    }
}
