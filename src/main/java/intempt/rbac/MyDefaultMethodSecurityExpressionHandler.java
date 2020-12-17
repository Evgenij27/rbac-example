package intempt.rbac;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import reactor.core.publisher.Flux;

public class MyDefaultMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Override
    public Object filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) {
        MethodSecurityExpressionOperations rootObject = (MethodSecurityExpressionOperations) ctx.getRootObject()
                .getValue();
        if (filterTarget instanceof Flux) {
            return filterFlux((Flux<?>) filterTarget, filterExpression, ctx, rootObject);
        }

        return super.filter(filterTarget, filterExpression, ctx);
    }

    private Object filterFlux(Flux<?> filterTarget, Expression filterExpression, EvaluationContext ctx,
                           MethodSecurityExpressionOperations rootObject) {
        return filterTarget.doOnNext(rootObject::setFilterObject)
                .filter(obj -> ExpressionUtils.evaluateAsBoolean(filterExpression, ctx));
    }
}
