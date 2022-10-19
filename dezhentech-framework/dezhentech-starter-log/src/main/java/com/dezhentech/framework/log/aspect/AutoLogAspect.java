package com.dezhentech.framework.log.aspect;

import com.dezhentech.framework.log.annotation.AutoLog;
import com.dezhentech.framework.log.model.AutoLogInfo;
import com.dezhentech.framework.log.properties.AutoLogProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自动日志切面
 *
 * @author yfcui
 * @date 2022/10/19
 */
@Aspect
@Slf4j
public class AutoLogAspect {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private AutoLogProperties autoLogProperties;

    private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}|{}";

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Before("@within(autoLog) || @annotation(autoLog)")
    public void beforeMethod(JoinPoint joinPoint, AutoLog autoLog) {
        //判断功能是否开启
        if (autoLogProperties.getEnabled()) {
            autoLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AutoLog.class);
            AutoLogInfo autoLogInfo = getAudit(autoLog, joinPoint);
            log.debug(MSG_PATTERN
                    , autoLogInfo.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                    , autoLogInfo.getApplicationName(), autoLogInfo.getClassName(), autoLogInfo.getMethodName()
                    , autoLogInfo.getUserId(), autoLogInfo.getUserName(), autoLogInfo.getClientId()
                    , autoLogInfo.getOperation());
        }
    }

    /**
     * 构建审计对象
     */
    private AutoLogInfo getAudit(AutoLog autoLog, JoinPoint joinPoint) {
        AutoLogInfo autoLogInfo = new AutoLogInfo();
        autoLogInfo.setTimestamp(LocalDateTime.now());
        autoLogInfo.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        autoLogInfo.setClassName(methodSignature.getDeclaringTypeName());
        autoLogInfo.setMethodName(methodSignature.getName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userId = request.getHeader("x-userid-header");
        String userName = request.getHeader("x-user-header");
        String clientId = request.getHeader("x-tenant-header");
        autoLogInfo.setUserId(userId);
        autoLogInfo.setUserName(userName);
        autoLogInfo.setClientId(clientId);

        String operation = autoLog.operation();
        if (operation.contains("#")) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            operation = getValBySpEL(operation, methodSignature, args);
        }
        autoLogInfo.setOperation(operation);

        return autoLogInfo;
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }
}
