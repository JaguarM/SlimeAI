package yannic.slimeai;

public class ClientProxyslimeai extends CommonProxyslimeai {

    @Override
    public void registerRenderers(slimeai instance) {
        instance.elements.forEach(element -> element.registerRenderers());
    }
}
