package hello.typeconverter.converter;


import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    @Test
    void stringToInteger(){
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void IntegerToString(){
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }


    @Test
    void stringToIpPort(){
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1",8080);
        String result = converter.convert(source);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void ipPortToString(){
        StringToIpPortConverter stringToIpPortConverter = new StringToIpPortConverter();
        IpPort result = stringToIpPortConverter.convert("127.0.0.1:8080");

        assertThat(result).isEqualTo(new IpPort("127.0.0.1",8080)); // Lombok에서 @EqualsAndHashCode를 IpPort 객체에 넣어두었기 때문에, 잘 작동한다.

        assertThat(result.getIp()).isEqualTo("127.0.0.1"); //이렇게 안해도된다.
        assertThat(result.getPort()).isEqualTo("8080"); //이렇게 안해도된다.
    }
}
