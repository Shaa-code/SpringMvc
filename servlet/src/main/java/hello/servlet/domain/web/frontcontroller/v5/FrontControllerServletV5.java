package hello.servlet.domain.web.frontcontroller.v5;

import hello.servlet.domain.web.frontcontroller.MyHandlerAdapter;
import hello.servlet.domain.web.frontcontroller.MyView;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.domain.web.frontcontroller.v4.ControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.domain.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    public FrontControllerServletV5() {

        initHandlerMappingMap();
        initHandlerAdapters();
    }


    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object handler = getHandler(req);
        if(handler == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(req);
        Map<String, Object> model = new HashMap<>(); //모델을 프론트 컨트롤러에서 처리
        String viewName = controllerV4.process(paramMap,model);

        MyView view = viewResolver(viewName);

        view.render(model,req,resp);
    }

    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        Object handler = handlerMappingMap.get(requestURI);
        return handler;
    }
}
