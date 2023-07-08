package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    @Override
    public Number parse(String text, Locale locale) throws ParseException, java.text.ParseException {
        log.info("text = {}, locale={}",text,locale);
        //"1,000" -> 1000
        NumberFormat format = NumberFormat.getInstance(locale);
        //바꾸는 코드가 직접 구현하려면 쉽지 않음, 미리 만들어둔거 쓰는거임.
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object = {}, locale ={}",object, locale);
        return NumberFormat.getInstance(locale).format(object);
    }

}
