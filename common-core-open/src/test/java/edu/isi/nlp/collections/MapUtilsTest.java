package edu.isi.nlp.collections;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import javax.annotation.Nullable;
import org.junit.Test;

public final class MapUtilsTest {

  @Test
  public void testAllowSameEntry() {
    final LaxImmutableMapBuilder<String, String> ret =
        MapUtils.immutableMapBuilderAllowingSameEntryTwice();
    ret.put("hello", "world");
    ret.put("hello", "world");
    ret.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAllowSameEntryFail() {
    final LaxImmutableMapBuilder<String, String> ret =
        MapUtils.immutableMapBuilderAllowingSameEntryTwice();
    ret.put("hello", "world");
    ret.put("hello", "earth");
    ret.build();
  }

  @Test
  public void testKeepFirst() {
    final LaxImmutableMapBuilder<String, String> ret =
        MapUtils.immutableMapBuilderIgnoringDuplicates();
    ret.put("hello", "world");
    ret.put("hello", "earth");
    final ImmutableMap<String, String> map = ret.build();
    assertEquals(1, map.size());
    assertEquals("world", map.get("hello"));
  }

  private static final Ordering<String> BY_REVERSE_STRING =
      Ordering.natural()
          .onResultOf(
              new Function<String, String>() {
                @Override
                public String apply(@Nullable final String s) {
                  return new StringBuilder(checkNotNull(s)).reverse().toString();
                }
              });

  @Test
  public void testBestByComparator() {
    final LaxImmutableMapBuilder<String, String> ret =
        MapUtils.immutableMapBuilderResolvingDuplicatesBy(BY_REVERSE_STRING);
    ret.put("hello", "world");
    ret.put("hello", "earth");
    final ImmutableMap<String, String> map = ret.build();
    assertEquals(1, map.size());
    assertEquals("earth", map.get("hello"));
  }
}
