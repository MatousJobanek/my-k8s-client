package my.k8s.client;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;

public class MyClient {

    public static void main(String[] args){

        ///home/mjobanek/cert/rootCA.key
        Config config = new ConfigBuilder().withMasterUrl("https://192.168.99.100:8443/").withCaCertFile(System.getProperty("user.dir" + "/") + "ca.crt").build();

        DefaultKubernetesClient client = new DefaultKubernetesClient(config);

        NamespaceList namespaceList = client.namespaces().list();
        ServiceList serviceList = client.services().list();
        ServiceList defaultServicesList = client.services().inNamespace("default").list();


    }
}
