package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet" , urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printStartLine(req);
        printHeaderLine(req);
        printHeaderUtils(req);
        printEtc(req);
    }

    private void printStartLine(HttpServletRequest req) {
        System.out.println("Request Line");
        System.out.println("req.getMethod : " + req.getMethod());
        System.out.println("req.getProtocol : " + req.getProtocol());
        System.out.println("req.getScheme : " + req.getScheme());
        System.out.println("req.getRequestURL : " + req.getRequestURL());
        System.out.println("req.getRequestURI : " + req.getRequestURI());
        System.out.println("req.getQueryString : " + req.getQueryString());
        System.out.println("req.isSecure : " + req.isSecure());
        System.out.println();
    }

    //Header 정보
    private void printHeaderLine(HttpServletRequest req){
        System.out.println("Header Line");

//        옛날 방식
//        Enumeration<String> headerNames = req.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            String headerName = headerNames.nextElement();
//            System.out.println(headerName + ": "+ headerName);
//        }

        req.getHeaderNames().asIterator()
                        .forEachRemaining(headerName -> System.out.println("headerName = " + headerName));

        req.getHeader("host"); //하나만 뽑고싶을 때 사용함.

        System.out.println();
    }

    private void printHeaderUtils(HttpServletRequest req){
        System.out.println("Header 편의 조회");
        System.out.println("[Host 편의 조회]");
        System.out.println("req.getServerName() = " + req.getServerName());
        System.out.println("req.getServerPort() = " + req.getServerPort());
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        req.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("req.getLocale() = " + req.getLocale());
        System.out.println();

        System.out.println("[Cookie 편의 조회]");
        if(req.getCookies() != null){
            for(Cookie cookie : req.getCookies()){
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회]");
        System.out.println("req.getContentType() = " + req.getContentType());
        System.out.println("req.getContentLength() = " + req.getContentLength());
        System.out.println("req.CharacterEncoding() = " + req.getCharacterEncoding());

        System.out.println();
    }

    private void printEtc(HttpServletRequest req){
        System.out.println("기타 조회");

        System.out.println("[Remote 정보]");
        System.out.println("req.getRemoteHost() = " + req.getRemoteHost());
        System.out.println("req.getRemoteAddr() = " + req.getRemoteAddr());
        System.out.println("req.getRemotePort() = " + req.getRemotePort());
        System.out.println();

        System.out.println("[Local 정보]");
        System.out.println("req.getLocalName() = " + req.getLocalName());
        System.out.println("req.getLocalAddr() = " + req.getLocalAddr());
        System.out.println("req.getLocalPort() = " + req.getLocalPort());

        System.out.println();
    }
}