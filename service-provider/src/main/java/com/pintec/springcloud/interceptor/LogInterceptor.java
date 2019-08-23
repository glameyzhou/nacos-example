/*
package com.pintec.springcloud.interceptor;

import com.google.gson.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

*/
/**
 * @date 2019.07.24.13. yang.zhou
 *//*

@Component
@Aspect
@Order(-1) //日志先行
public class LogInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);


    //跳过http servlet request|response的序列化、统一将byte数组转为"ignore byte data..."输出，避免将日志打爆
    private static final Gson GSON = new GsonBuilder()
            */
/*.setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return clazz == HttpServletRequest.class || clazz == HttpServletResponse.class;
                }
            })*//*

            .registerTypeHierarchyAdapter(byte[].class, new BytesIgnoreTypeAdapter())
            .create();

    */
/**
     * @around 无法处理void类型的方法，因此需要排除掉
     *//*

//    @Pointcut("execution(!void com.pintec.springcloud.controller.*.*(..)) && @annotation(com.pintec.springcloud.interceptor.Log)")
    @Pointcut("@annotation(com.pintec.springcloud.interceptor.Log)")
    public void pointcutExpression() {

    }

    @Around("pointcutExpression()")
    public Object doRoundLog(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = null;
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String methodName = method.getName();
        String simpleName = pjp.getTarget().getClass().getSimpleName();

        */
/*
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletInputStream inputStream = request.getInputStream();
        String requestBody = IOUtils.toString(inputStream);
        System.out.println(requestBody);
        *//*


        */
/*
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        byte[] bytes = ByteStreams.toByteArray(request.getInputStream());
        ByteSource byteSource = ByteSource.wrap(bytes);
        InputStream newInputStream = byteSource.openStream();
        String requestBody = IOUtils.toString(newInputStream);
        logger.info("requestBody {}", requestBody);
        proceed = pjp.proceed();
        *//*


        try {
            List<Object> inputParameters = buildInputParameters(pjp.getArgs());
            logger.info("[request] [{}.{}] {}", simpleName, methodName, GSON.toJson(inputParameters));

            proceed = pjp.proceed();

        } finally {
            if (method.getReturnType().isAssignableFrom(Void.class)) {
                logger.info("[response] [{}.{}] void", simpleName, methodName);
                return null;
            }
            if (proceed == null) {
                logger.info("[response] [{}.{}] null", simpleName, methodName);
                return null;
            }
            if (method.getReturnType().isAssignableFrom(ModelAndView.class)) {
                Map<String, Object> model = ((ModelAndView) proceed).getModel();
                logger.info("[response] [{}.{}] {}", simpleName, methodName, GSON.toJson(model));
            } else {
                logger.info("[response] [{}.{}] {}", simpleName, methodName, GSON.toJson(proceed));
            }
        }
        return proceed;
    }

    private List<Object> buildInputParameters(Object[] args) {
        if (args == null) {
            return null;
        }
        //排除掉HttpServletRequest HttpServletResponse
        return Arrays.stream(args)
                .filter(arg -> arg != null && !(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse))
                .collect(Collectors.toList());
    }

    private static class BytesIgnoreTypeAdapter implements JsonSerializer<byte[]> {
        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive("ignore byte data...");
        }
    }
}
*/
