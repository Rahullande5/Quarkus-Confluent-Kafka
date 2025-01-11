package org.acme;

import java.io.StringReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class XmlUnmarshaller<T> {

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public T unmarshalXmlToObject(final String accountingRequestXml) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(accountingRequestXml));
    }
}