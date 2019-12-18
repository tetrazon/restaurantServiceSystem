import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TestWrongEmail {
    @Test
    public void validateEmailTest(){
        String wrongMail = "dfsdail.ru";
        assertFalse(utils.FieldsValidation.validateEmail(wrongMail));
    }
}
