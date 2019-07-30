package com.pintec.springcloud.interceptor;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * MVC拦截器。必然要先于AOP方法级别的@annotation
 * {@link org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)}
 *
 * @author yang.zhou
 * @date 2019.07.25.14.
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandler");
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod method = (HandlerMethod) handler;
            log.info("preHandler {}", method.getMethod().getName());

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler 处理完毕");
        if (modelAndView != null) {
            Map<String, Object> model = modelAndView.getModel();
            log.info("postHandler {}", new Gson().toJson(model));
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion 视图封装完毕");
    }
}
