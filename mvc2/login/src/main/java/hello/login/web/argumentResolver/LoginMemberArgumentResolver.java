package hello.login.web.argumentResolver;


import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        log.info("supportParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // @Login Member loginMember 에서 "loginMember"라는 파라미터에 애노테이션이 붙어있는지 알아보는 함수이다.

        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        // @Login Member loginMember 에서 파라미터의 타입인 "Member"가 할당이 되었는지 확인하는 함수이다.

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolverArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if( session == null ){
            return null;
            //@Login Member에 null이 들어간다.
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 세션에 멤버가 있으면 @Login Member에 세션의 멤버가 들어간다.
    }


}
