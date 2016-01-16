package hu.illesjosh.pairwise.ross;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.Iterables;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

public class OptimumPairs {
	private static final String ROSS_5_FILE = "ross-5.txt";
	private static final String ROSS_7_FILE = "ross-7.txt";
	private static final String ROSS_9_FILE = "ross-9.txt";
	private static final String ROSS_11_FILE = "ross-11.txt";

	private static final int ROSS_MIN = 4;
	private static final int ROSS_MAX = 11;

	private static final Logger logger = LoggerFactory.getLogger(OptimumPairs.class);

	private static final Map<Integer, List<Pair<Integer>>> guides = Maps.newHashMap();;

	static {
		try {
			guides.put(5, loadRossTable(ROSS_5_FILE, 5));
			guides.put(4, getDegradedGuide(4));

			guides.put(7, loadRossTable(ROSS_7_FILE, 7));
			guides.put(6, getDegradedGuide(6));

			guides.put(9, loadRossTable(ROSS_9_FILE, 9));
			guides.put(8, getDegradedGuide(8));

			guides.put(11, loadRossTable(ROSS_11_FILE, 11));
			guides.put(10, getDegradedGuide(10));
		} catch (InvalidRossTableException irte) {
			logger.error(irte.getMessage());
		}
	}

	public static List<Pair<Integer>> getGuide(int n) throws InvalidRossTableException {
		if (guides.containsKey(n)) {
			return guides.get(n);
		} else {
			throw new InvalidRossTableException("Stimuli count not supported: " + n);
		}
	}

	public static <T> List<Pair<T>> create(List<T> stimuli) throws InvalidRossTableException {
		final int n = stimuli.size();
		final List<Pair<Integer>> guide = getGuide(n);
		final List<Pair<T>> result = new ArrayList<Pair<T>>();

		for (Pair<Integer> g : guide) {
			Pair<T> p = new Pair<T>(stimuli.get(g.left - 1), stimuli.get(g.right - 1));
			result.add(p);
		}

		return result;
	}

	private static List<Pair<Integer>> loadRossTable(final String filename, final int n)
			throws InvalidRossTableException {

		final List<Pair<Integer>> result;

		if (n < ROSS_MIN || n > ROSS_MAX) {
			throw new InvalidRossTableException("Stimuli count not supported: " + n);
		}

		result = new ArrayList<Pair<Integer>>(n * (n - 1) / 2);

		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line;
			for (int lineNumber = 1; (line = reader.readLine()) != null; ++lineNumber) {
				if (lineNumber > (n * (n - 1) / 2)) {
					throw new InvalidRossTableException("Too many lines [" + filename + ":" + lineNumber + "]");
				}

				String[] pairStr = line.trim().split("\\s+", 2);
				if (pairStr.length != 2) {
					throw new InvalidRossTableException("Not a pair [" + filename + ":" + lineNumber + "]");
				}

				int left = Integer.parseInt(pairStr[0]);
				if (left < 1 || left > n) {
					throw new InvalidRossTableException(
							"Left value out of bounds [" + filename + ":" + lineNumber + "]");
				}
				int right = Integer.parseInt(pairStr[1]);
				if (right < 1 || right > n) {
					throw new InvalidRossTableException(
							"Right value out of bounds [" + filename + ":" + lineNumber + "]");
				}
				result.add(new Pair<Integer>(left, right));
			}
		} catch (IOException e) {
			throw new InvalidRossTableException("Couldn't read data file: " + filename);
		}

		/* Completeness & balance check */
		final int[] leftFreq = new int[n + 1];
		final int[] rightFreq = new int[n + 1];
		for (Pair<Integer> p : result) {
			++leftFreq[p.left];
			++rightFreq[p.right];
		}
		for (int i = 1; i <= n; ++i) {
			if (leftFreq[i] != (n - 1) / 2 || rightFreq[i] != (n - 1) / 2) {
				throw new InvalidRossTableException("Wrong frequency: " + i);
			}
		}

		/*
		 * TODO Check this: "maximum spacing: the number of pairs which appear
		 * between a first occurrence and every subsequent occurrence of the
		 * same pair element ist maximised"
		 */

		return ImmutableList.copyOf(result);
	}

	private static List<Pair<Integer>> getDegradedGuide(final int n) throws InvalidRossTableException {
		final List<Pair<Integer>> original = getGuide(n + 1);
		final List<Pair<Integer>> result = ImmutableList
				.copyOf((Iterables.filter(original, new Predicate<Pair<Integer>>() {
					@Override
					public boolean apply(final Pair<Integer> input) {
						return input.left <= n && input.right <= n;
					}
				})));
		return result;
	}

	public static class Pair<T> {
		public final T left;
		public final T right;

		public Pair(T left, T right) {
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return "(" + left.toString() + ", " + right.toString() + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
			return result;
		}
	}
}
