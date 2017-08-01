import com.github.itonyli.common.entity.URL;
import com.github.itonyli.register.ZKRegister;

public class Main {

    public static void main(String[] args) throws Exception {
        URL url = new URL();
        url.setAppName("demo");
        url.setServiceName("hello");
        url.setProviders(true);
        System.out.println(ZKRegister.build().getProviderRoutes(url));
    }
}
