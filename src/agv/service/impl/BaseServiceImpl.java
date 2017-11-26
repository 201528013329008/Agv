package agv.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import agv.module.SystemModuleI;

public class BaseServiceImpl{
	
	@Autowired
	protected SystemModuleI systemModule;
	
	protected Logger systemLogger = Logger.getLogger("agv");
	protected Logger userLogger = Logger.getLogger("agv.user");
	
}
