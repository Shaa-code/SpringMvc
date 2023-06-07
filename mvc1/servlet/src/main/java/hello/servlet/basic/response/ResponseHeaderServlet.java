package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "responseHeaderServlet",urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //status 설정
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        //header 설정
        resp.setHeader("Content-Type", "text/plain");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");

        //header를 메서드로 만들어 편하게 정의할 수 도 있음.
        //content(resp);
        //cookie(resp);
        redirect(resp);


        //message body도 이렇게 넣을 수 있다.
        PrintWriter writer = resp.getWriter();
        writer.print("ok");

    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset-utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type","text/plain;charset=utf-8");
        //response.setContentLength(2); 생략해도 자동으로 생성된다.
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie","myCookie=good; Max-Age=600");
        //위 처럼 setHeader로 쿠키를 파라미터로 넣어줘도되지만, 아래처럼 객체로 만들어 response에 넣어줘도 된다.
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
    //Status Code: 302
    //Location: /basic/hello-form.html

    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    //response.setHeader("Location", "/basic/hello-form.html");
    //위처럼 Location Header와 리다이렉트 주소를 넣어줄 수도 있다.
    //아래 처럼 한줄로 처리해도 똑같은 로직으로 처리된다.
    response.sendRedirect("/basic/hello-form.html");

    }

}
