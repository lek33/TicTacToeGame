package tictactoe;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class TaskQueueServlet {
	Queue queue = QueueFactory.getDefaultQueue();
}
