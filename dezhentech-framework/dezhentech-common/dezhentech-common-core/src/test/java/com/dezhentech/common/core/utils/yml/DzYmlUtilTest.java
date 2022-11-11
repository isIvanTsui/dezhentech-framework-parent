package com.dezhentech.common.core.utils.yml;

import com.dezhentech.common.core.utils.UtilsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = DzYmlUtilTest.class)
class DzYmlUtilTest {

    @Test
    void getProperty() {
    }


    @Test
    void hasKey() {
    }

    @Test
    void autoConfig() {
        UtilsProperties utilsProperties = DzYmlUtil.autoConfig(UtilsProperties.class);

        assertEquals("Dezhentech.com?q=1234", utilsProperties.getEncryption().getKey());
        assertEquals(StandardCharsets.UTF_8, utilsProperties.getCharset());

        //指定yml文件
        DzYmlUtil customYmlUtil = new DzYmlUtil("application-dev.yml");

        List phoneNumberList = customYmlUtil.getProperty("test.contact.phoneNumbers[]");
        assertEquals(2,phoneNumberList.size());
        assertEquals("%$#", customYmlUtil.getProperty("test.contact.phoneNumbers[0]"));
        assertNull(customYmlUtil.getProperty("test.contact.phoneNumbers[1]"));

        //默认yml文件
        DzYmlUtil ymlUtil = DzYmlUtil.getInstance();

        List phoneNumberList1 = ymlUtil.getProperty("test.contact.phoneNumbers[]");
        assertEquals(2,phoneNumberList1.size());
        assertEquals("%$#", ymlUtil.getProperty("test.contact.phoneNumbers[0]"));
        assertEquals("UTF-8", ymlUtil.getProperty("test.contact.phoneNumbers[1]"));

    }

}