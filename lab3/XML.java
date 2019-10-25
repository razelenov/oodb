package company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;

public class XML {

    public void saveXML(Shop object) throws JAXBException, IOException {
        try {
            JAXBContext context = JAXBContext.newInstance(Shop.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            m.marshal(object, sw);
            String s = sw.toString();
            FileWriter fileWriter = new FileWriter("shop.xml");
            fileWriter.write(s);
            fileWriter.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Shop loadXML() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Shop.class);

        Unmarshaller um = context.createUnmarshaller();
        File file = new File("shop.xml");
        String str = "";
        if (file.exists()) {
            str = new String(Files.readAllBytes(file.toPath()));
        }
        StringReader reader = new StringReader(str);
        Shop shop = (Shop) um.unmarshal(new StreamSource(new StringReader(str)));
        return shop;
    }
}
