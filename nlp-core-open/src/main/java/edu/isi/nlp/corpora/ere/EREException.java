package edu.isi.nlp.corpora.ere;

import edu.isi.nlp.xml.XMLUtils;
import org.w3c.dom.Element;

public class EREException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EREException(String msg) {
    super(msg);
  }

  public EREException(String msg, Throwable t) {
    super(msg, t);
  }

  public static EREException forElement(Element e, Throwable t) {
    return new EREException("While processing element " + XMLUtils.dumpXMLElement(e), t);
  }

  public static EREException forElement(String msg, Element e, Throwable t) {
    return new EREException(
        "While processing element " + XMLUtils.dumpXMLElement(e) + ", " + msg, t);
  }

  public static EREException forElement(String msg, Element e) {
    return new EREException("While processing element " + XMLUtils.dumpXMLElement(e) + ", " + msg);
  }
}
