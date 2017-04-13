package my.k8s.client;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;

public class MyClient {

    public static void main(String[] args) {

        ///home/mjobanek/cert/rootCA.key
        Config config = new ConfigBuilder().withMasterUrl("https://192.168.99.100:8443/")
            .withUsername("admin")
            .withPassword("admin")
            .withCaCertFile("/my-k8s-client/" + "ca.crt")
            .build();

        DefaultOpenShiftClient client = new DefaultOpenShiftClient(config);

        System.out.println(client.getApiVersion());

        NamespaceList namespaceList = client.namespaces().list();
        System.out.println(namespaceList.getItems());
        ServiceList serviceList = client.services().list();
        System.out.println(serviceList.getItems());
        ServiceList defaultServicesList = client.services().inNamespace("default").list();
        System.out.println(defaultServicesList.getItems());
    }
}
