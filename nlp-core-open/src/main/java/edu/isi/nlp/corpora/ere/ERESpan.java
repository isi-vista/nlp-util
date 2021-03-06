package edu.isi.nlp.corpora.ere;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import edu.isi.nlp.strings.offsets.CharOffset;
import edu.isi.nlp.strings.offsets.OffsetRange;

public final class ERESpan {
  public final int start;
  public final int end;
  public final String text;

  private ERESpan(final int start, final int end, final String text) {
    this.start = start;
    this.end = end;
    this.text = checkNotNull(text);
  }

  public static ERESpan from(final int start, final int end, final String text) {
    return new ERESpan(start, end, text);
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public String getText() {
    return text;
  }

  public OffsetRange<CharOffset> asCharOffsets() {
    return OffsetRange.charOffsetRange(getStart(), getEnd());
  }

  @Override
  public String toString() {
    return text + "[" + start + ":" + end + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(start, end, text);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    ERESpan other = (ERESpan) obj;
    return (start == other.start) && (end == other.end) && text.equals(other.text);
  }
}
