package pe.com.gesatepedws.batch;

import java.util.Calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@ComponentScan("pe.com.gesatepedws.batch")
public class QuartzLoader {

	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
		MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
		factoryBean.setTargetBeanName("NotificacionService");
		factoryBean.setTargetMethod("notificarTodo");
		return factoryBean;
	}
	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
		factoryBean.setStartDelay(3000);
		factoryBean.setName("notificacionTrigger");
		factoryBean.setGroup("notificacionGroup");
		
		//TODO Obtener hora de ejecución desde BD
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 26);
		calendar.add(Calendar.MINUTE, 1);
		
		factoryBean.setCronExpression("0 " + 
				calendar.get(Calendar.MINUTE) + 
				" " + 
				calendar.get(Calendar.HOUR_OF_DAY) + 
				" 1/1 * ? *");
		return factoryBean;
	}
	
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
		factoryBean.setTriggers(cronTriggerFactoryBean().getObject());
		return factoryBean;
	}
	
}
