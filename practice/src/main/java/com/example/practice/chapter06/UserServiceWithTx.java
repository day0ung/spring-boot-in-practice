package com.example.practice.chapter06;

import com.example.practice.chapter05.User;
import com.example.practice.chapter05.UserDao;
import com.example.practice.chapter05.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

// 6.4 스프링의 ProxyFactoryBean - 어드바이스 + 포인트컷 조합으로 트랜잭션 적용
public class UserServiceWithTx {

    // ProxyFactoryBean으로 트랜잭션 프록시 생성 예시
    public static Object createTransactionalProxy(
        UserDao userDao,
        MailSender mailSender,
        PlatformTransactionManager txManager
    ) throws Exception {

        UserServiceImpl target = new UserServiceImpl(userDao);
        target.setMailSender(mailSender);

        // 어드바이스 설정
        TransactionInterceptor advice = new TransactionInterceptor();
        advice.setTransactionManager(txManager);
        Properties txAttributes = new Properties();
        txAttributes.setProperty("upgrade*", "PROPAGATION_REQUIRED");
        txAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        advice.setTransactionAttributes(txAttributes);

        // 포인트컷 설정
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("upgrade*", "get*", "add");

        // ProxyFactoryBean 생성
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, advice));

        return pfBean.getObject();
    }
}
