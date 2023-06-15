package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }

        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000){
            errors.rejectValue("price","range");
        }

        if(item.getQuantity() == null || item.getQuantity() > 10000){
            errors.rejectValue("quantity","max");
        }
    }
}
