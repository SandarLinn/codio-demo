package com.codigo.demo.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sandar linn
 * @CreatedAt June 3, 2023
 */

@Target({ TYPE })
@Retention(RUNTIME)
@Documented
@Component
@RestController
@RequestMapping()
public @interface APIVersion {
	@AliasFor(annotation = RequestMapping.class, attribute = "path")
	String apiPath() default "/api/v1/";
}
