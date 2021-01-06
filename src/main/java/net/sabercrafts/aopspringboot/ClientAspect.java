package net.sabercrafts.aopspringboot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ClientAspect {

	private static Logger logger = LoggerFactory.getLogger(ClientAspect.class);
	private static long timer; 
	
	
	@After("execution(* net.sabercrafts.aopspringboot..*.*(..))")
	public void test(JoinPoint joinPoint) {
		logger.info("methode test appelé!");
		System.out.println(joinPoint.getTarget().getClass());
	}
	
	@Before("execution(* net.sabercrafts.aopspringboot.Client.verser(*))")
	public void beforeVerser() {
		logger.info("---- Operation de Versement ");
		timer = System.currentTimeMillis();
	}
	
	@After("execution(* net.sabercrafts.aopspringboot.Client.verser(..))")
	public void afterVerser(JoinPoint joinPoint) {
		Client client = (Client) joinPoint.getTarget();
		double mt = (Double) joinPoint.getArgs()[0];
		logger.info("Le client " + client.getNom() + " a versé une somme de "+mt+"dh");
		logger.info("Le nouveau solde est " + client.getCp().getSolde());
		logger.info(" Cette operation a pris " + (System.currentTimeMillis() - timer) + "ms");
		logger.info("---- Fin Operation");
	}
	
	 @Around("execution(* net.sabercrafts.aopspringboot.Client.retirer(*))")
	    public Object aroundRetirer(ProceedingJoinPoint joinPoint) throws Throwable{
		 logger.info("---- Operation de Retrait");
			timer = System.currentTimeMillis();
			Object o = null;
			
			double mt = (Double) joinPoint.getArgs()[0];
			Client client = (Client) joinPoint.getTarget();
			
			logger.info("Verification du solde");
			
			if (client.getCp().getSolde() < mt) {
				logger.info("Echec. Solde insuffisant.");
			} else {
				logger.info("Solde disponible.");
				o = joinPoint.proceed();
				
				logger.info("Le client " + client.getNom() + " a retiré une somme de "+mt+"dh");
				logger.info("Le nouveau solde est " + client.getCp().getSolde());
				logger.info(" Cette operation a pris " + (System.currentTimeMillis() - timer) + "ms");
			}

			logger.info("---- Fin Operation");

			return o;
	    }
	
}
