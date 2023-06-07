package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    public String helloBasic(){
        log.info("hi");
        return "ok";
    }

    @GetMapping(value = "/hello/users/{userId}/orders/{orderId}", headers = "mode=hi")
    public String mappingMethod(@PathVariable String userId, @PathVariable String orderId){
        log.info("log.info = {} {}",userId,orderId);
        return "ok";
    }

    @PostMapping(value = "/hello/consume", consumes = "application/json")
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }



}
