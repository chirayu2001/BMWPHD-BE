package net.bmw.bmwphd.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.interfaces.RSAPrivateKey;

@Component
@ConfigurationPropertiesBinding
public class MyPrivateKeyConverter implements Converter<String, RSAPrivateKey> {
    @Override
    public RSAPrivateKey convert(String from) {
        System.out.println(from);
        return RsaKeyConverters.pkcs8().convert(new ByteArrayInputStream(from.getBytes()));
    }
}
