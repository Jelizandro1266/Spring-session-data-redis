package com.session.redis;

import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.SessionRepositoryFilter;

@SpringBootApplication
@EnableRedisHttpSession()
public class SessionRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionRedisApplication.class, args);
	}
	
	/**
	 * @param sessionRepository
	 * @param servletContext
	 * @return
	 *  contiene la configuracion para agregar un nombre de cookie especifico, de lo contrario Spring utilizara
	 *  SESSION como cookie principal para guardar la session en redis
	 */
	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(
			SessionRepository<S> sessionRepository, ServletContext servletContext) {
		SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
		sessionRepositoryFilter.setServletContext(servletContext);
		CookieHttpSessionStrategy httpSessionStrategy = new CookieHttpSessionStrategy();
		httpSessionStrategy.setCookieSerializer(cookieSerializer());
		httpSessionStrategy.setSessionAliasParamName("BDMMicroservicios");
		sessionRepositoryFilter.setHttpSessionStrategy(httpSessionStrategy);
		return sessionRepositoryFilter;
	}

	/**
	 * @return
	 * Bena que devuelve la configuracion necesaria de la cookie
	 */
	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID");
		return serializer;
	}

	/**
	 * @return
	 * Bean con la conexion a redis
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);

		System.out.println("Carga Coneccion");
		return jedisConFactory;
	}

	
	/**
	 * @return 
	 * Bean con la creacion del RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
	
	
	/**
	 * @return
	 * configuracion para agregar un name space al registro de session, 
	 * el maximo tiempo de vida de la session en segundos,
	 * Y el metodo de guardado en redis
	 * (RedisFlushMode.IMMEDIATE= realiza los cambios en BD de manera inmediata cada que se altera la session,
	 *  RedisFlushMode.ON_SAVE = realiza los cambios en BD hasta que la peticion http es despachada.)
	 */
	@Bean
	public RedisOperationsSessionRepository sessionRepository() {
		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(jedisConnectionFactory());
		
		sessionRepository.setDefaultMaxInactiveInterval(60);
		sessionRepository.setRedisKeyNamespace("demo:redis");
		
		sessionRepository.setRedisFlushMode(RedisFlushMode.IMMEDIATE);
		return sessionRepository;
	}
	
	
}
