import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FIeldValidationTest {
    @Test
    public void validateEmailTest(){
        String wrongMail = "tom@mail.ru";
        assertTrue(utils.FieldsValidation.validateEmail(wrongMail));
    }
}
