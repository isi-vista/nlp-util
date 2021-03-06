package edu.isi.nlp.strings.formatting;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import edu.isi.nlp.strings.offsets.AnnotatedOffsetRange;
import edu.isi.nlp.strings.offsets.CharOffset;
import edu.isi.nlp.strings.offsets.OffsetRange;
import edu.isi.nlp.symbols.Symbol;
import java.io.IOException;
import org.junit.Test;

public class XMLStyleAnnotationFormatterTest {

  private static final Symbol ELLIPSES = Symbol.from("ELLIPSES");
  private static final Symbol FUTURE = Symbol.from("FUTURE");
  private static final Symbol PP = Symbol.from("PP");
  private static final Symbol LINE = Symbol.from("LINE");
  private static final Symbol FOO = Symbol.from("FOO");
  private static final Symbol BAR = Symbol.from("BAR");

  @Test
  public void xmlAnnotationTest() throws IOException {
    final String originalText =
        "Time present and time past / Are both perhaps present in time future...";

    final ImmutableList<AnnotatedOffsetRange<CharOffset>> annotations =
        ImmutableList.of(
            AnnotatedOffsetRange.create(LINE, OffsetRange.charOffsetRange(0, 25)),
            AnnotatedOffsetRange.create(BAR, OffsetRange.charOffsetRange(1, 1)),
            AnnotatedOffsetRange.create(LINE, OffsetRange.charOffsetRange(29, 67)),
            AnnotatedOffsetRange.create(PP, OffsetRange.charOffsetRange(54, 67)),
            AnnotatedOffsetRange.create(ELLIPSES, OffsetRange.charOffsetRange(68, 70)),
            AnnotatedOffsetRange.create(FOO, OffsetRange.charOffsetRange(0, 11)));

    final String expectedResult =
        "<LINE><FOO>T<BAR>i</BAR>me present</FOO> and time past</LINE> "
            + "/ <LINE>Are both perhaps present <PP>in time future</PP></LINE><ELLIPSES>...</ELLIPSES>";

    assertEquals(
        expectedResult, XMLStyleAnnotationFormatter.create().format(originalText, annotations));
  }
}
