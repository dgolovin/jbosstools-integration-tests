package org.jboss.ide.eclipse.as.ui.bot.test.eap4;

import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.JBossServer;
import org.jboss.ide.eclipse.as.ui.bot.test.template.OperateServerTemplate;
import org.jboss.reddeer.requirements.server.ServerReqState;

/**
 * @see OperateServerTemplate
 * @author Lucia Jelinkova
 *
 */
@JBossServer(state=ServerReqState.STOPPED, type=ServerReqType.EAP4_3)
public class OperateEAP4Server extends OperateServerTemplate {

	@Override
	public String getWelcomePageText() {
		return "JBoss Management";
	}
	
	@Override
	protected boolean ignoreExceptionInConsole(){
		return true;
	}
	
	@Override
	public boolean setHeadlessModeOnMac() {
		return true;
	}
}
