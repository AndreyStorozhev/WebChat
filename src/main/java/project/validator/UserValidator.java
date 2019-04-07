package project.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.entity.UserDetails;
import project.service.UserDetailsService;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDetails.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDetails userDetails = (UserDetails) o;
        if (userDetailsService.findByLogin(userDetails.getLogin()) != null)
            errors.rejectValue("login", "nonUinc.login.msg");
        if (userDetails.getLogin().length() < 3 || userDetails.getLogin().length() > 20)
            errors.rejectValue("login", "validation.login.Size.message");
        if (userDetails.getPassword().length() < 6)
            errors.rejectValue("password", "validation.password.Size.message");
        if (userDetails.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]$"))
            errors.rejectValue("password", "validation.password.regex.message");
        if (!userDetails.getPassword().equals(userDetails.getConfirmPassword()))
            errors.rejectValue("confirmPassword", "validation.confirmPassword.err.message");
        if (!userDetails.getEmail().matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            errors.rejectValue("email", "validation.email.regex.message");
        }
    }
}
