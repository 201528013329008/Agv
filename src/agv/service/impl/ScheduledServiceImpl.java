package agv.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import agv.service.AgvServiceI;
import agv.service.ScheduledServiceI;
import agv.service.TaskServiceI;

@Service
public class ScheduledServiceImpl extends BaseServiceImpl implements ScheduledServiceI {
	@Autowired
	private AgvServiceI agvService;
	
	@Autowired
	private TaskServiceI taskService;
	
	@Override
	synchronized public void init() {

	}

	@Override
	public void sendToSytem(String msg) {
		
	}

	@Override
	public void run() {
		
	}

	

}
