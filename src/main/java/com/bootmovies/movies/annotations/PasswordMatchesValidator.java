package com.bootmovies.movies.annotations;

import com.bootmovies.movies.domain.user.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
   public void initialize(PasswordMatches constraint) {
   }

   @Override
   public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
      UserDTO userDTO = (UserDTO) o;
      return userDTO.getPassword().equals(userDTO.getMatchingPassword());
   }

}
