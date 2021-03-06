package domain.calculator;

import domain.calculator.exception.InvalidInputException;

import java.util.List;
import java.util.regex.Pattern;

public class ExpressionValidator {

    public void validateTokenList(List<String> tokenList) {
        long invalidTokenCount = tokenList.stream()
                .filter(token -> !isNumber(token) && !isOperator(token))
                .count();

        if (invalidTokenCount > 0) {
            throw new InvalidInputException("올바르지 않은 입력입니다.");
        }
    }

    public void validateTokenSequence(List<String> tokenList) {
        throwIfConditionIsTrue(hasNotAnyToken(tokenList));

        String firstToken = tokenList.get(0);
        throwIfConditionIsTrue(!isNumber(firstToken));

        for (int i = 1; i < tokenList.size(); i += 2) {
            String operatorToken = tokenList.get(i);
            throwIfConditionIsTrue(!isOperator(operatorToken));

            if (isNotLastOperation(i, tokenList.size())) {
                String rightOperandToken = tokenList.get(i + 1);
                throwIfConditionIsTrue(!isNumber(rightOperandToken));
            }
        }
    }

    private void throwIfConditionIsTrue(boolean isConditionTrue) {
        if (isConditionTrue) {
            throw new InvalidInputException("올바르지 않은 입력입니다.");
        }
    }

    private boolean hasNotAnyToken(List<String> tokenList) {
        return (tokenList.size() == 0);
    }
    private boolean isNumber(String token) {
        return Pattern.matches("^[0-9]+$", token);
    }
    private boolean isOperator(String token) {
        return Pattern.matches("\\+|-|\\*|/", token);
    }
    private boolean isNotLastOperation(int index, int tokenListSize) {
        return (index + 1 < tokenListSize);
    }
}