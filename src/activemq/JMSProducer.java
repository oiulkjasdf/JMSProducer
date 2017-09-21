package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {
	
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL="tcp://192.168.116.128:61616";
	private static final int SENDNUM=10;
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory;//连接工厂
		Connection connection = null;//连接
		Session session;//会话 接受或者发送消息的线程
		Destination destination;//消息的目的地
		MessageProducer messageProducer;//消息生产者
		
		connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
				
		try {
			connection=connectionFactory.createConnection();//通过连接工厂获取连接
			connection.start();
			session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
//			destination=session.createQueue("FirstQueue");
			destination=session.createTopic("FirstTopic1");
			messageProducer=session.createProducer(destination);
			sendMessage(session, messageProducer);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void sendMessage(Session session,MessageProducer messageProducer) throws JMSException{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMessage 发送的消息"+i);
			System.out.println("发布的消息:"+"ActiveMQ 发布的消息"+ i);
			messageProducer.send(message);
		}
	}
}
