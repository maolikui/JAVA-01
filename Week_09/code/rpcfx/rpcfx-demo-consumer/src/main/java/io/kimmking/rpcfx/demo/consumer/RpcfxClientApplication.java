package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.annotation.RpcfxReferenceScan;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RpcfxReferenceScan("io.kimmking.rpcfx.demo.api")
@RestController
@RequestMapping("/test")
public class RpcfxClientApplication {
    @Autowired
    private UserService userService;

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) {

        // UserService service = new xxx();
        // service.findById

        // UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
        // User user = userService.findById(1);
        // System.out.println("find user id=1 from server: " + user.getName());
        //
        // OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
        // Order order = orderService.findOrderById(1992129);
        // System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

        //
        // UserService userService2 = Rpcfx.createFromRegistry(UserService.class, "localhost:2181", new TagRouter(), new RandomLoadBalancer(), new CuicuiFilter());

        SpringApplication.run(RpcfxClientApplication.class, args);
    }

    @GetMapping("/user")
    public User test() {
        User u = userService.findById(1);
        return u;
    }
    // private static class TagRouter implements Router {
    // 	@Override
    // 	public List<String> route(List<String> urls) {
    // 		return urls;
    // 	}
    // }
    //
    // private static class RandomLoadBalancer implements LoadBalancer {
    // 	@Override
    // 	public String select(List<String> urls) {
    // 		return urls.get(0);
    // 	}
    // }
    //
    // @Slf4j
    // private static class CuicuiFilter implements Filter {
    // 	@Override
    // 	public boolean filter(RpcfxRequest request) {
    // 		log.info("filter {} -> {}", this.getClass().getName(), request.toString());
    // 		return true;
    // 	}
    // }
}



