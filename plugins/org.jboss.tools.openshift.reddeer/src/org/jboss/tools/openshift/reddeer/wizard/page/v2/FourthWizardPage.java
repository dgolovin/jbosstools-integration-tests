package org.jboss.tools.openshift.reddeer.wizard.page.v2;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.condition.ButtonWithTextIsEnabled;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.tools.openshift.reddeer.utils.OpenShiftLabel;

/**
 * 
 * Fourth wizard page of a New application wizard. This page is related
 * to git configuration.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class FourthWizardPage {

	
	public FourthWizardPage() {
		new DefaultShell(OpenShiftLabel.Shell.NEW_APP_WIZARD);
		
		// Wait until data are processed - there is no other way currently
		new WaitUntil(new ButtonWithTextIsEnabled(new FinishButton()), TimePeriod.LONG);
	}
	
	public void setGitDestination(String destination) {
		if (destination != null) {
			if (new CheckBox(0).isChecked()) {
				new CheckBox(0).click();
			}
			
			new LabeledText("Git Clone Destination:").setText(destination);
		}
	}
	
	public void setGitRemoteName(String newRemoteName) {
		if (newRemoteName != null) {
			if (new CheckBox(1).isChecked()) {
				new CheckBox(1).click();
			}
			
			new LabeledText("Remote name:").setText(newRemoteName);
		}
	}
}
