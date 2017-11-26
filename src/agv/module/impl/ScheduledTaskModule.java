package agv.module.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import agv.service.AutoTaskServiceI;

@Component("scheduledTaskModule")
public class ScheduledTaskModule {
	
	private static final  Logger systemLogger = Logger.getLogger("agv");
	private static final Logger userLogger = Logger.getLogger("agv.user");
	
	@Autowired
	private AutoTaskServiceI autoTaskService;
	
	/** 
     * 定时卡点计算。每10秒执行一次
     */  
    //@Scheduled(cron = "0/10 * * * * ?")  
	public void run(){
		//systemLogger.info("ScheduledTask Run");
    	autoTaskService.autoShelfTrayTask();
	}
    
}
