package org.eternity.movie.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eternity.movie.generic.Money;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="policy_type")
@NoArgsConstructor @Getter
public abstract class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="POLICY_ID")
    @org.hibernate.annotations.BatchSize(size = 2)
    private Set<DiscountCondition> conditions = new HashSet<>();

    public DiscountPolicy(Set<DiscountCondition> conditions) {
        this.conditions = conditions;
    }
}
