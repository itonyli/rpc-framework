import com.github.itonyli.common.entity.Request;
import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.serialize.*;
import com.github.itonyli.common.utils.SnowFlake;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class SerializerTest {

    private List<Request> requests = Lists.newArrayList();

    @Before
    public void setup() {
        for (int i = 0; i < 100000; i++) {
            requests.add(toInstance());
        }
    }

    @Test
    public void test() {
        //serializer(new JDKSerializer(), request);
        //serializer(new KryoSerializer(), request);
        //serializer(new HessianSerializer(), request);
        serializer(new ProtostuffSerializer(), requests.get(0));
    }

    @Test
    public void test_cost() {
        test_cost_time(new JDKSerializer(), requests);
        test_cost_time(new KryoSerializer(), requests);
        test_cost_time(new ProtostuffSerializer(), requests);
        test_cost_time(new HessianSerializer(), requests);
    }

    public void test_cost_time(Serializer serializer, List<Request> requests) {
        long t1 = System.currentTimeMillis();
        for (Request request : requests) {
            serializer(serializer, request);
        }
        System.out.println("--" + serializer.getClass().getName() + "-->" + (System.currentTimeMillis() - t1));
    }


    public static void serializer(Serializer serializer, Request request) {
        long t1 = System.currentTimeMillis();
        byte[] bytes = serializer.serialize(request);
        //System.out.println(serializer.getClass().getSimpleName() + " --bytes size--> " + bytes.length);

        Request obj = serializer.deserialize(bytes, Request.class);
        //System.out.println(serializer.getClass().getSimpleName() + " --cost time--> " + (System.currentTimeMillis() - t1));
        verification(obj);
    }


    public static Request toInstance() {
        String now = String.valueOf(System.currentTimeMillis());

        Route route = new Route();
        route.setAddress("Address" + now);
        route.setPort(new Random().nextInt(65535));

        List<String> list = Lists.newArrayList(now);

        Request request = new Request();
        request.setRequestId(SnowFlake.generateID());
        request.setServiceName("Service" + now);
        request.setMethodName("Method" + now);
        request.setArgsClass(new Class[]{Route.class, List.class});
        request.setArgs(new Object[]{route, list});

        return request;
    }

    public static void verification(Object obj) {
        if (obj instanceof Request) {
            Request p2 = (Request) obj;
            //System.out.println(p2);
        } else {
            System.out.println("$$" + obj);
        }
    }
}

