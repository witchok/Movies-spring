package com.bootmovies.movies.annotations;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValuesMatchValidator implements ConstraintValidator<FieldsValuesMatch, Object> {
   private String field;
   private String fieldMatch;

   public void initialize(FieldsValuesMatch constraint) {
      this.field = constraint.field();
      this.fieldMatch = constraint.fieldMatch();
   }

   @Override
   public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
      Object fieldValue = new BeanWrapperImpl(o).getPropertyValue(field);
      Object fieldValueMatch = new BeanWrapperImpl(o).getPropertyValue(fieldMatch);
      if (fieldValue != null){
         return fieldValue.equals(fieldValueMatch);
      }else {
         return fieldValueMatch == null;
      }
   }

}
