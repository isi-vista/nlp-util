package edu.isi.nlp;

import com.google.common.base.Optional;
import edu.isi.nlp.strings.offsets.CharOffset;
import edu.isi.nlp.strings.offsets.OffsetRange;
import edu.isi.nlp.strings.offsets.UTF16Offset;
import org.immutables.value.Value;

/**
 * A {@link UnicodeFriendlyString} which does not contain a non-BMP character. This class should
 * never be referenced directly. Always create {@link UnicodeFriendlyString}s via {@link
 * StringUtils#unicodeFriendly(String)}. See the interface Javadoc for details.
 */
@IsiNlpImmutable
@Value.Immutable
abstract class StringWithoutNonBmp extends AbstractUnicodeFriendlyString
    implements UnicodeFriendlyString {

  @Override
  public abstract String utf16CodeUnits();

  @Override
  public final boolean hasNonBmpCharacter() {
    return false;
  }

  @Override
  public boolean hasNonBmpCharacter(OffsetRange<CharOffset> characterRange) {
    return false;
  }

  public static UnicodeFriendlyString of(String utf16CodeUnits) {
    return new StringWithoutNonBmp.Builder().utf16CodeUnits(utf16CodeUnits).build();
  }

  @Override
  public int lengthInUtf16CodeUnits() {
    return utf16CodeUnits().length();
  }

  @Override
  public int lengthInCodePoints() {
    return lengthInUtf16CodeUnits();
  }

  @Override
  public int codepointAtCodepointIndex(final CharOffset codepointIdx) {
    // for this class codepoint indices are guaranteed to equal code unit indices
    return utf16CodeUnits().codePointAt(codepointIdx.asInt());
  }

  @Override
  public CharOffset codepointIndex(UTF16Offset offset) {
    // if all characters are in the BMP then there is a 1-1 code unit to code point mapping
    return CharOffset.asCharOffset(offset.asInt());
  }

  @Override
  public UnicodeFriendlyString substringByCodePoints(final CharOffset startCodepointInclusive) {
    return StringWithoutNonBmp.of(utf16CodeUnits().substring(startCodepointInclusive.asInt()));
  }

  @Override
  public UnicodeFriendlyString substringByCodePoints(
      final CharOffset startCodepointInclusive, final CharOffset endCodepointExclusive) {
    return StringWithoutNonBmp.of(
        utf16CodeUnits().substring(startCodepointInclusive.asInt(), endCodepointExclusive.asInt()));
  }

  @Override
  public boolean isEmpty() {
    return utf16CodeUnits().isEmpty();
  }

  @Override
  public UnicodeFriendlyString trim() {
    return StringWithoutNonBmp.of(utf16CodeUnits().trim());
  }

  @Override
  public final Optional<CharOffset> codePointIndexOf(
      UnicodeFriendlyString other, CharOffset startIndex) {
    if (startIndex.asInt() < 0 || startIndex.asInt() > lengthInCodePoints()) {
      throw new IndexOutOfBoundsException("StartIndex was out of bounds: " + startIndex);
    }
    final int offset = utf16CodeUnits().indexOf(other.utf16CodeUnits(), startIndex.asInt());
    if (offset >= 0) {
      return Optional.of(CharOffset.asCharOffset(offset));
    } else {
      return Optional.absent();
    }
  }

  @Override
  public final <T> void processCodePoints(CodePointProcessor<T> codePointProcessor) {
    for (int i = 0; i < utf16CodeUnits().length(); ++i) {
      codePointProcessor.processCodepoint(
          this, CharOffset.asCharOffset(i), utf16CodeUnits().codePointAt(i));
    }
  }

  @Override
  public final String toString() {
    return super.toString();
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  @Override
  public final boolean equals(Object o) {
    return super.equals(o);
  }

  public static class Builder extends ImmutableStringWithoutNonBmp.Builder {}
}
