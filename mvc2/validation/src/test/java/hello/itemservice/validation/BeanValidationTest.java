package hello.itemservice.validation;


import org.junit.jupiter.api.Test;

public class BeanValidationTest {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItem(" ");
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for (Set<ConstraintViolation<Item>> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation).getMessage();
        }
    }
}
