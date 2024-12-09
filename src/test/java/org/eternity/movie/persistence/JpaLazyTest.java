package org.eternity.movie.persistence;

import jakarta.persistence.EntityManager;
import org.eternity.movie.generic.Money;
import org.eternity.movie.reservation.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
public class JpaLazyTest {
	@Autowired
	private EntityManager em;

	@Test
	public void add_discount_condition() {
		fixture().forEach(em::persist);
		em.flush();
		em.clear();

		List<DiscountPolicy> policies = em.createQuery("select p from DiscountPolicy p", DiscountPolicy.class).getResultList();

		policies.stream().forEach(policy -> policy.getConditions().size());
	}

    @Test
    public void default_batch_fetch_size_test() {
        fixture().forEach(em::persist);
        em.flush();
        em.clear();

        // default 옵션으로도 컨트롤 가능
        em.setProperty("hibernate.default_batch_fetch_size", 2);

        List<DiscountPolicy> policies = em.createQuery("select p from DiscountPolicy p", DiscountPolicy.class).getResultList();

        policies.stream().forEach(policy -> policy.getConditions().size());
    }

	List<DiscountPolicy> fixture() {
		return List.of(
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new AmountDiscountPolicy(
						Money.wons(1000),
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet())),
				new PercentDiscountPolicy(
						0.1,
						IntStream.range(0,100).mapToObj(SequenceCondition::new).collect(Collectors.toSet()))
				);
	}
}
