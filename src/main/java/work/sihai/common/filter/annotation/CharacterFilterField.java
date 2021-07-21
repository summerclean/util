package work.sihai.common.filter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author max_sun
 * @date 2020/9/10 11:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface CharacterFilterField {
    String regex() default "[^\u2E80-\u2FFF\u31C0-\u31E3\u3400-\u4DB5\u4E00-\u9FEF\u3400-\u4DB5a-zA-Z0-9_\"',，.。/、【】！!?？—<>%;‘’；)《（）》(&+=`“”·*#@$￥：:~\\\\\\-]";
}
