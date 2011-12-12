/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.seam3;


import org.jboss.tools.cdi.bot.test.annotations.CDIWizardType;
import org.junit.Test;

/**
 * Test operates on resource openOn in Seam3 using CDI tools
 * 
 * @author Jaroslav Jankovic
 */

public class Seam3ResourceOpenOnTest extends Seam3TestBase {

	@Override
	public String getProjectName() {
		return "Seam3ResourceOpenOnTest";
	}

	/**
	 * https://issues.jboss.org/browse/JBIDE-8202
	 */	
	@Test
	public void testResourceOpenOn() {
			
		String className = "Bean1";
		
		wizard.createCDIComponent(CDIWizardType.BEANS_XML, "beans.xml", getProjectName() + "/WebContent/WEB-INF", null);		
		
		wizard.createCDIComponentWithContent(CDIWizardType.BEAN, className, 
				getPackageName(), null, "/resources/seam3/openon/BeanWithResourceAnnotation.java.cdi");
		editResourceUtil.replaceInEditor("BeanComponent", className);		
		
		openOnUtil.openOnByOption("beansXml", className + ".java", "Open Resource");
		
		String destinationFile = getEd().getTitle();		
		assertTrue("ERROR: redirected to " + destinationFile,
					destinationFile.equals("beans.xml"));

		editResourceUtil.moveFileInProjectExplorer("beans.xml", getProjectName() + "/WebContent/WEB-INF",
								  getProjectName() + "/WebContent/META-INF");
		LOGGER.info("bean.xml was moved to META-INF");
		
		setEd(bot.swtBotEditorExtByTitle(className + ".java"));
		editResourceUtil.replaceInEditor("WEB", "META");
		openOnUtil.openOnByOption("beansXml", className + ".java", "Open Resource");
		
		destinationFile = getEd().getTitle();
		assertTrue("ERROR: redirected to " + destinationFile,
				   destinationFile.equals("beans.xml"));

	}
	
}
	