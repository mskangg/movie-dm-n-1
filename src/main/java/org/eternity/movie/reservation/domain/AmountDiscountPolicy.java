package org.eternity.movie.reservation.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.movie.generic.Money;

import java.util.Set;

@Entity
@DiscriminatorValue("AMOUNT")
@NoArgsConstructor @Getter
public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount,
                                Set<DiscountCondition> conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }
}
