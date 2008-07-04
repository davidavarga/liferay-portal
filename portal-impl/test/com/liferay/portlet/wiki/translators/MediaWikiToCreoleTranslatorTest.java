/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.wiki.translators;

import com.liferay.portal.util.InitUtil;

import junit.framework.TestCase;

/**
 * <a href="MediaWikiToCreoleTranslatorTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class MediaWikiToCreoleTranslatorTest extends TestCase {

	static {
		InitUtil.init();
	}

	public MediaWikiToCreoleTranslatorTest() {
		_translator = new MediaWikiToCreoleTranslator();
	}

	public void testCleanUnnecessaryHeaderEmphasis1() throws Exception {
		String content = "= '''title''' =";

		String expected = "== title ==";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testCleanUnnecessaryHeaderEmphasis2() throws Exception {
		String content = "== '''title''' ==";

		String expected = "=== title ===";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testCleanUnnecessaryHeaderEmphasis3() throws Exception {
		String content = "=== '''title''' ===";

		String expected = "==== title ====";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHeader3() throws Exception {
		String content = "=== Header 3 ===";

		String expected = "==== Header 3 ====";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHeader2() throws Exception {
		String content = "== Header 2 ==";

		String expected = "=== Header 2 ===";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHeader1() throws Exception {
		String content = "= Header 1 =";

		String expected = "== Header 1 ==";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHeader1WithoutSpaces() throws Exception {
		String content = "=Header 1=";

		String expected = "==Header 1==";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testAngleBracketsUnscape() throws Exception {
		String content = "&lt;div&gt;";

		String expected = "<div>";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testCategoryRemoval() throws Exception {
		String content =
			"[[Category:My category]]\n[[category:Other category]]";

		String expected = "";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testBoldItalics() throws Exception {
		String content = "This is ''''bold and italics''''.";

		String expected = "This is **//bold and italics//**.";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testBold() throws Exception {
		String content = "This is '''bold'''.";

		String expected = "This is **bold**.";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testItalics() throws Exception {
		String content = "This is ''italics''.";

		String expected = "This is //italics//.";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testImage() throws Exception {
		String content = "[[Image:sample.png]]";

		String expected = "{{SharedImages/sample.png}}";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testLinkWithLabel() throws Exception {
		String content = "[[Link|This is the label]]";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testURL() throws Exception {
		String content = "text[http://www.liferay.com]text";

		String expected = "text[[http://www.liferay.com]]text";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testURLWithText1() throws Exception {
		String content = "text [http://www.liferay.com link text] text";

		String expected = "text [[http://www.liferay.com|link text]] text";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testURLWithText2() throws Exception {
		String content = "text [[http://www.liferay.com link text]] text";

		String expected = "text [[http://www.liferay.com|link text]] text";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testURLWithLabel() throws Exception {
		String content = "[http://www.liferay.com This is the label]";

		String expected = "[[http://www.liferay.com|This is the label]]";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testTermDefinition() throws Exception {
		String content = "\tterm:\tdefinition";

		String expected = "**term**:\ndefinition";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testIndentedParagraph() throws Exception {
		String content = "\t:\tparagraph";

		String expected = "paragraph";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testMonospace() throws Exception {
		String content = "previous line\n monospace\nnext line";

		String expected = "previous line\n{{{\n monospace\n}}}\nnext line";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testMultilinePre() throws Exception {
		String content = "previous line\n monospace\n second line\nnext line";

		String expected =
			"previous line\n{{{\n monospace\n second line\n}}}\nnext line";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testNowiki() throws Exception {
		String content =
			"previous line\n<pre>\nmonospace\nsecond line\n</pre>\nnext line";

		String expected =
			"previous line\n{{{\nmonospace\nsecond line\n}}}\nnext line";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHtmlPre() throws Exception {
		String content =
			"previous line\n<pre>\nmonospace\nsecond line\n</pre>\nnext line";

		String expected =
			"previous line\n{{{\nmonospace\nsecond line\n}}}\nnext line";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testUserReference() throws Exception {
		String content = "--[[User:User name]]";

		String expected = "User name";
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testNotListItem() throws Exception {
		String content = "\t*item";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testListItem() throws Exception {
		String content = "* item";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testListSubItem() throws Exception {
		String content = "** subitem";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testListSubSubItem() throws Exception {
		String content = "*** subsubitem";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testOrderedListItem() throws Exception {
		String content = "# item";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testOrderedListSubItem() throws Exception {
		String content = "## subitem";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testOrderedListSubSubItem() throws Exception {
		String content = "### subsubitem";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testHorizontalRule() throws Exception {
		String content = "\n----";

		String expected = content;
		String actual = _translate(content);

		assertEquals(expected, actual);
	}

	public void testNowikiWithFormat() throws Exception {
		String content =
			"previous line\n<nowiki>\nmonospace\n''second'' " +
				"line\n</nowiki>\nnext line";

		String expected =
			MediaWikiToCreoleTranslator.TABLE_OF_CONTENTS +
				"previous line\n{{{\nmonospace\n''second'' line\n}}}\nnext" +
					" line";
		String actual = _translator.translate(content);

		assertEquals(expected, actual);
	}

	public void testLinkWithUnderscores() throws Exception {
		String content = "[[Link_With_Underscores]]";

		String expected =
			MediaWikiToCreoleTranslator.TABLE_OF_CONTENTS +
				"[[Link With Underscores]]";
		String actual = _translator.postProcess(_translate(content));

		assertEquals(expected, actual);
	}

	public String _translate(String content) {
		return _translator.runRegexps(content);
	}

	private MediaWikiToCreoleTranslator _translator;

}