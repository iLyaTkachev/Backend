package com.example.ilyat.myapplication.backend;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Calculator {

    private static List<Character> getSymbols(final String pLine) {
        final List<Character> listOfSymbols = new LinkedList<>();
        for (int i = 0; i < pLine.length(); i++) {
            final char symbol = pLine.charAt(i);

            if (symbol == '-' || symbol == '+' || symbol == '*' || symbol == '/') {
                listOfSymbols.add(symbol);
            }
        }
        return listOfSymbols;
    }

    private static List<String> getOperands(final String pLine) {
        final String[] operandsArray = pLine.split("-|\\+|\\*|/");
        final List<String> listOfOperands = new LinkedList<>();

        Collections.addAll(listOfOperands, operandsArray);

        return listOfOperands;
    }

    private static void listUpdater(final List<Character> listOfSymbols, final List<String> listOfOperands, final int position, final float result) {
        listOfSymbols.remove(position);
        listOfOperands.remove(position);
        listOfOperands.remove(position);
        listOfOperands.add(position, String.valueOf(result));
    }

    public static float calculate(String pLine) {
        final List<Character> listOfSymbols = getSymbols(pLine);
        final List<String> listOfOperands = getOperands(pLine);
        int operationCount = listOfSymbols.size();
        float operand1;
        float operand2;
        float result;

        while (operationCount > 0) {
            if (listOfSymbols.contains('*') || listOfSymbols.contains('/')) {
                final int currentPositionMultiplication = listOfSymbols.indexOf('*');
                final int currentPositionDividation = listOfSymbols.indexOf('/');

                if ((currentPositionMultiplication < currentPositionDividation && currentPositionMultiplication != -1) || currentPositionDividation == -1) {
                    operand1 = Float.parseFloat(listOfOperands.get(currentPositionMultiplication));
                    operand2 = Float.parseFloat(listOfOperands.get(currentPositionMultiplication + 1));
                    result = operand1 * operand2;

                    listUpdater(listOfSymbols, listOfOperands, currentPositionMultiplication, result);
                } else if ((currentPositionMultiplication > currentPositionDividation && currentPositionDividation != -1) || currentPositionMultiplication == -1) {
                    operand1 = Float.parseFloat(listOfOperands.get(currentPositionDividation));
                    operand2 = Float.parseFloat(listOfOperands.get(currentPositionDividation + 1));
                    result = operand1 / operand2;

                    listUpdater(listOfSymbols, listOfOperands, currentPositionDividation, result);
                }

            } else if (listOfSymbols.contains('-') || listOfSymbols.contains('+')) {
                final int currentPositionSubstraction = listOfSymbols.indexOf('-');
                final int currentPositionAddition = listOfSymbols.indexOf('+');

                if ((currentPositionSubstraction < currentPositionAddition && currentPositionSubstraction != -1) || currentPositionAddition == -1) {
                    operand1 = Float.parseFloat(listOfOperands.get(currentPositionSubstraction));
                    operand2 = Float.parseFloat(listOfOperands.get(currentPositionSubstraction + 1));
                    result = operand1 - operand2;

                    listUpdater(listOfSymbols, listOfOperands, currentPositionSubstraction, result);
                } else if ((currentPositionSubstraction > currentPositionAddition && currentPositionAddition != -1) || currentPositionSubstraction == -1) {

                    operand1 = Float.parseFloat(listOfOperands.get(currentPositionAddition));
                    operand2 = Float.parseFloat(listOfOperands.get(currentPositionAddition + 1));
                    result = operand1 + operand2;

                    listUpdater(listOfSymbols, listOfOperands, currentPositionAddition, result);
                }

            }
            operationCount--;
        }

        final Iterator<String> iterator = listOfOperands.iterator();

        String finalResult = "";

        while (iterator.hasNext()) {
            finalResult = iterator.next();
        }

        return Float.parseFloat(finalResult);
    }
}
