package hello.login.web.filter;


import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest httpRequest = (HttpServletRequest)request;
         String requestURI = httpRequest.getRequestURI();

         HttpServletResponse httpResponse = (HttpServletResponse)response;

         try{
             log.info("인증 체크 필터 시작 {}", requestURI);

             if(isLoginCheckPath(requestURI)){
                 log.info("인증 체크 로직 실행 {}" ,requestURI);
                 HttpSession session = httpRequest.getSession(false);
                 if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                     log.info("미인증 사용자 요청 {}", requestURI);
                     //로그인으로 redirect
                     httpResponse.sendRedirect("/login?redirectURL=" + requestURI); //로그인을 성공시켰을 때, 다시 내가 원래 작업하던 페이지로 보내주는 코드이다.
                     return;
                 }
             }
             chain.doFilter(request, response);
         }catch( Exception e ){
             throw e; //예외의 로깅만해도 된다. 하지만, 톰캣이 오류를 잡아낼 수 있도록 보내주어야한다.
         } finally {
            log.info("인증 체크 필터 종료 {} ", requestURI);
         }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
