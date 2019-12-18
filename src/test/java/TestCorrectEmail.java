import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestCorrectEmail {
    @Test
    public void validateEmailTest(){
        String goodMail = "lolo@mail.ru";
        assertTrue(utils.FieldsValidation.validateEmail(goodMail));
    }
}
