import com.liquid.utils.OkHttpUtils;

/**
 * 测试类
 *
 * @author Lkmao
 */
public class Test {
    public static void main(String[] args) {
        String url = "http://localhost:8801";
        String result = OkHttpUtils.getInstance().get(url, null);
        System.out.println(result);
    }
}
