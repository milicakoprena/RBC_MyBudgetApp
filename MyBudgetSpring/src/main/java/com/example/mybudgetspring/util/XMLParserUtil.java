package com.example.mybudgetspring.util;


import com.example.mybudgetspring.model.entities.AccountEntity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XMLParserUtil {
    private static final String XMLDATA_FILENAME = "my_budget_data.xml";

    public static List<AccountEntity> parseXMLData() throws IOException, JAXBException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(XMLDATA_FILENAME);

        if (is == null) {
            throw new IOException("File not found: " + XMLDATA_FILENAME);
        }

        JAXBContext jc = JAXBContext.newInstance(AccountEntityWrapper.class);

        Unmarshaller u = jc.createUnmarshaller();
        AccountEntityWrapper wrapper = (AccountEntityWrapper) u.unmarshal(new StreamSource(is));
        return wrapper.getAccounts();
    }
}
