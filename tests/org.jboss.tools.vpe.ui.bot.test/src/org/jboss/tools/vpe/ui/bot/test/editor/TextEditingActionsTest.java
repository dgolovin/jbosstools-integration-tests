/*******************************************************************************

 * Copyright (c) 2007-2010 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.ui.bot.test.editor;

import java.awt.event.KeyEvent;
import java.io.File;

import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.tools.ui.bot.ext.Assertions;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTEclipseExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.helper.KeyboardHelper;
import org.jboss.tools.ui.bot.ext.parts.SWTBotEditorExt;
import org.jboss.tools.ui.bot.ext.view.ProblemsView;
import org.jboss.tools.vpe.ui.bot.test.VPEAutoTestCase;
import org.jboss.tools.vpe.ui.bot.test.tools.SWTBotWebBrowser;
import org.mozilla.interfaces.nsIDOMNode;
/**
 * Tests JSP file Editing and Cut, Copy, Paste actions through Visual Editor Menu for Text selection  
 * @author vlado pakan
 *
 */
public class TextEditingActionsTest extends VPEEditorTestCase {
  
  private SWTBotExt botExt = null;
  
  private static final String TEXT_TO_EDIT = "Text to edit";
  
  private static final String PAGE_TEXT = "<%@ taglib uri=\"http://java.sun.com/jsf/core\" prefix=\"f\"%>\n" +
    "<%@ taglib uri=\"http://java.sun.com/jsf/html\" prefix=\"h\"%>\n" +
    "<html>\n" +
    "  <head>\n" +
    "    <title>Input User Name Page</title>\n" +
    "  </head>\n" + 
    "  <body>\n" +
    "    <f:view>\n" +
    "      <h:outputText value=\"" + TextEditingActionsTest.TEXT_TO_EDIT + "\"/>\n" +
    "    </f:view>\n" +
    "  </body>\n" +
    "</html>";
    
  private static final String TEST_PAGE_NAME = "TextEditingActionsTest.jsp";
  
  private SWTBotEditorExt jspEditor;
  private SWTBotWebBrowser webBrowser;
  
	public TextEditingActionsTest() {
		super();
		botExt = new SWTBotExt();
	}
	@Override
	public void setUp() throws Exception {
	  super.setUp();
    eclipse.maximizeActiveShell();
    createJspPage(TextEditingActionsTest.TEST_PAGE_NAME);
    util.waitForNonIgnoredJobs();
    jspEditor = botExt.swtBotEditorExtByTitle(TextEditingActionsTest.TEST_PAGE_NAME);
    webBrowser = new SWTBotWebBrowser(TextEditingActionsTest.TEST_PAGE_NAME,botExt);	  
    
	}
	/**
	 * Tests Cut Copy Paste Operations on Blank Page
	 */
	public void testCutCopyPasteBlankPage(){
	  
	  jspEditor.setText("");
    jspEditor.save();
    bot.sleep(Timing.time3S());
	  jspEditor.setText(TextEditingActionsTest.TEXT_TO_EDIT);
	  jspEditor.save();
	  bot.sleep(Timing.time3S());
  	// Check Copy Functionality
	  String textToCutCopy = TextEditingActionsTest.TEXT_TO_EDIT.substring(0,4);
	  nsIDOMNode node = webBrowser.getDomNodeByTagName("SPAN", 0);
	  webBrowser.selectDomNode(node, 0);
	  jspEditor.deselectAndSetCursorPosition(0, 0);
	  webBrowser.setFocus();
	  KeyboardHelper.selectTextUsingAWTKeyBoard(true,textToCutCopy.length());
	  webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.COPY_MENU_LABEL);
	  jspEditor.deselectAndSetCursorPosition(0, TextEditingActionsTest.TEXT_TO_EDIT.length());
	  webBrowser.setFocus();
	  webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.PASTE_MENU_LABEL);
	  jspEditor.save();
	  bot.sleep(Timing.time2S());
	  String textToContain = TextEditingActionsTest.TEXT_TO_EDIT + textToCutCopy;
	  Assertions.assertSourceEditorContains(jspEditor.getText(),
	      textToContain, 
	      TextEditingActionsTest.TEST_PAGE_NAME);
	  assertVisualEditorContainsNodeWithValue(webBrowser, 
	      textToContain, 
	      TextEditingActionsTest.TEST_PAGE_NAME);
  	// Check Cut Functionality
	  webBrowser.selectDomNode(node, 0);
	  jspEditor.deselectAndSetCursorPosition(0, 0); 
    webBrowser.setFocus();
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_RIGHT, textToContain.length());
    KeyboardHelper.selectTextUsingAWTKeyBoard(false, textToCutCopy.length());
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.CUT_MENU_LABEL);
    jspEditor.save();
    bot.sleep(Timing.time2S());
    jspEditor.deselectAndSetCursorPosition(0, 0);
    Assertions.assertSourceEditorIs(jspEditor.getText(),
        TextEditingActionsTest.TEXT_TO_EDIT, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    assertVisualEditorContainsNodeWithValue(webBrowser, 
        TextEditingActionsTest.TEXT_TO_EDIT, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    webBrowser.setFocus();
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.PASTE_MENU_LABEL);
    jspEditor.save();
    bot.sleep(Timing.time2S());
    textToContain = textToCutCopy + TextEditingActionsTest.TEXT_TO_EDIT;
    Assertions.assertSourceEditorContains(jspEditor.getText(),
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    assertVisualEditorContainsNodeWithValue(webBrowser, 
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
	}

	 /**
   * Tests insert Enter in Visual Editor
   */
  public void testInsertEnter(){
    
    jspEditor.setText(TextEditingActionsTest.PAGE_TEXT);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    nsIDOMNode node = webBrowser.getDomNodeByTagName("SPAN", 0);
    webBrowser.selectDomNode(node, 0);
    bot.sleep(Timing.time1S());
    // Check inserting Enter Functionality
    Position cursorPosition = jspEditor.cursorPosition();
    jspEditor.deselectAndSetCursorPosition(cursorPosition.line, cursorPosition.column);
    webBrowser.setFocus();
    KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_RIGHT);
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_ENTER, 6);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    String jspEditorText = jspEditor.getText();
    assertTrue ("Source Editor should has text " + TextEditingActionsTest.PAGE_TEXT +
        "\nbut it is\n" + jspEditorText,
        jspEditorText.equals(TextEditingActionsTest.PAGE_TEXT));
  }

  /**
   * Tests page editing via keyboard Enter in Visual Editor
   */
  public void testKeyboardEditing(){
    
    jspEditor.setText(TextEditingActionsTest.PAGE_TEXT);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    nsIDOMNode node = webBrowser.getDomNodeByTagName("SPAN", 0);
    webBrowser.selectDomNode(node, 0);
    bot.sleep(Timing.time1S());
    // Check inserting Enter Functionality
    Position cursorPosition = jspEditor.cursorPosition();
    jspEditor.deselectAndSetCursorPosition(cursorPosition.line, cursorPosition.column);
    webBrowser.setFocus();
    KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_RIGHT);
    KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_LEFT);
    String testText = "insertedTextDeleteBackspace";
    KeyboardHelper.typeBasicStringUsingAWT(testText);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    String sourceEditorText = jspEditor.getText();
    String textToContain = "<h:outputText value=\"" + testText;
    assertTrue ("Source Editor has to containt text " + textToContain +
        "\nbut it doesn't.\nSource Editor text is:\n" + sourceEditorText,
        sourceEditorText.contains(textToContain));
    // Test Backspace
    int lengthToRemove = 9;
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_BACK_SPACE, lengthToRemove);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    textToContain = textToContain.substring(0, textToContain.length() - lengthToRemove);
    assertTrue ("Source Editor has to containt text " + textToContain +
        "\nbut it doesn't.\nSource Editor text is:\n" + sourceEditorText,
        sourceEditorText.contains(textToContain));
    // Test Delete
    lengthToRemove = 6;
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_LEFT, lengthToRemove);
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_DELETE, lengthToRemove);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    textToContain = textToContain.substring(0, textToContain.length() - lengthToRemove);
    assertTrue ("Source Editor has to containt text " + textToContain +
        "\nbut it doesn't.\nSource Editor text is:\n" + sourceEditorText,
        sourceEditorText.contains(textToContain));
    
  }
  
	@Override
	protected void closeUnuseDialogs() {

	}

	@Override
	protected boolean isUnuseDialogOpened() {
		return false;
	}
  @Override
  public void tearDown() throws Exception {
    jspEditor.close();
    super.tearDown();
  }
  /**
   * Tests Cut Copy Paste Operations on Value Attribute
   */
  public void testCutCopyPasteValueAttribute(){
    
    jspEditor.setText(TextEditingActionsTest.PAGE_TEXT);
    jspEditor.save();
    bot.sleep(Timing.time3S());
    nsIDOMNode node = webBrowser.getDomNodeByTagName("SPAN", 0);
    webBrowser.selectDomNode(node, 0);
    bot.sleep(Timing.time1S());
  	// Check Copy Functionality
    Position cursorPosition = jspEditor.cursorPosition();
    jspEditor.deselectAndSetCursorPosition(cursorPosition.line, cursorPosition.column);
    webBrowser.setFocus();
    String textToCutCopy = TextEditingActionsTest.TEXT_TO_EDIT.substring(0,4);
    KeyboardHelper.selectTextUsingAWTKeyBoard(true,textToCutCopy.length());
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.COPY_MENU_LABEL);
    webBrowser.setFocus();
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_RIGHT, 
        TextEditingActionsTest.TEXT_TO_EDIT.length() - textToCutCopy.length());
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.PASTE_MENU_LABEL);
    jspEditor.save();
    bot.sleep(Timing.time2S());
    String textToContain = TextEditingActionsTest.TEXT_TO_EDIT + textToCutCopy;
    Assertions.assertSourceEditorContains(jspEditor.getText(),
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    assertVisualEditorContainsNodeWithValue(webBrowser, 
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
  	// Check Cut Functionality
    webBrowser.selectDomNode(node, 0);
    bot.sleep(Timing.time2S());
    jspEditor.deselectAndSetCursorPosition(cursorPosition.line, cursorPosition.column); 
    webBrowser.setFocus();
    KeyboardHelper.typeKeyCodeUsingAWTRepeately(KeyEvent.VK_RIGHT, textToContain.length());
    KeyboardHelper.selectTextUsingAWTKeyBoard(false, textToCutCopy.length());
    bot.sleep(Timing.time2S());
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.CUT_MENU_LABEL);
    jspEditor.save();
    bot.sleep(Timing.time2S());
    Assertions.assertSourceEditorContains(jspEditor.getText(),
        "\"" + TextEditingActionsTest.TEXT_TO_EDIT + "\"", 
        TextEditingActionsTest.TEST_PAGE_NAME);
    assertVisualEditorContainsNodeWithValue(webBrowser, 
        TextEditingActionsTest.TEXT_TO_EDIT, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    bot.sleep(Timing.time2S());
    webBrowser.selectDomNode(node, 0);
    bot.sleep(Timing.time2S());
    jspEditor.deselectAndSetCursorPosition(cursorPosition.line, cursorPosition.column);
    bot.sleep(Timing.time2S());
    webBrowser.setFocus();
    bot.sleep(Timing.time2S());
    KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_RIGHT);
    bot.sleep(Timing.time2S());
    KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_LEFT);
    webBrowser.clickContextMenu(node,
        SWTBotWebBrowser.PASTE_MENU_LABEL);
    bot.sleep(Timing.time2S());
    jspEditor.save();
    bot.sleep(Timing.time2S());
    textToContain = textToCutCopy + TextEditingActionsTest.TEXT_TO_EDIT;
    Assertions.assertSourceEditorContains(jspEditor.getText(),
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    assertVisualEditorContainsNodeWithValue(webBrowser, 
        textToContain, 
        TextEditingActionsTest.TEST_PAGE_NAME);
    SWTBotTreeItem[] errors = ProblemsView.getFilteredErrorsTreeItems(botExt, 
        null, 
        File.separator + VPEAutoTestCase.JBT_TEST_PROJECT_NAME, 
        TextEditingActionsTest.TEST_PAGE_NAME,
        null);
    assertTrue("There were these errors when editing page "
        + TextEditingActionsTest.TEST_PAGE_NAME 
        + ": " 
        + SWTEclipseExt.getFormattedTreeNodesText(errors),
        errors == null || errors.length == 0);
  } 
}
