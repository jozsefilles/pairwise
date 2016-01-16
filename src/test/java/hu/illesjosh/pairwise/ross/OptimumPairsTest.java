package hu.illesjosh.pairwise.ross;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

public class OptimumPairsTest {

	@Test
	public void testCreate() throws InvalidRossTableException {
		final List<Integer> list5 = ImmutableList.of(1, 2, 3, 4, 5);
		List<?> actual = OptimumPairs.create(list5);
		List<?> expected = OptimumPairs.getGuide(5);
		assertEquals(actual, expected);

		final List<Integer> list10 = ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		actual = OptimumPairs.create(list10);
		expected = OptimumPairs.getGuide(10);
		assertEquals(actual, expected);
	}

}
