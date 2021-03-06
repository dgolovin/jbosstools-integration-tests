package org.jboss.tools.arquillian.ui.bot.reddeer.maven;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;

public class UpdateMavenProjectDialog {

private static final String NAME = "Update Maven Project";
	
	public void open(Project project){
		project.select();
		new ContextMenu("Maven","Update Project...").select();
	}

	public void ok(){
		activate();
		String shellText = new DefaultShell().getText();
		Button button = new PushButton("OK");
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	public void forceUpdate(boolean force){
		new CheckBox("Force Update of Snapshots/Releases").toggle(force);
	}
	
	private void activate(){
		// sets focus
		new DefaultShell(NAME);
	}
}
