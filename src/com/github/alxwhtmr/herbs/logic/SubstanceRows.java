package com.github.alxwhtmr.herbs.logic;

import java.util.ArrayList;


/**
 * The helper {@code SubstanceRows} class
 * that contains a list of all substances from
 * all goods that were entered by the user
 *
 * @since 29.12.2014
 */
public class SubstanceRows {
    private ArrayList<String> allRows;

    public SubstanceRows() {
        allRows = new ArrayList<String>();
    }

    public ArrayList<String> getAllRows() {
        return allRows;
    }

    public void appendRows(ArrayList<Substance> substances) {
        if (allRows.isEmpty()) {
            for (Substance substance : substances) {
                allRows.add(substance.getName());
            }
        } else {
            for (Substance verifiableSubstance : substances) {
                boolean found = false;
                for (String verifiedSubstance : allRows) {
                    if (verifiableSubstance.getName().equalsIgnoreCase(verifiedSubstance)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allRows.add(verifiableSubstance.getName());
                }
            }
        }
    }

    public void printRows() {
        for (String row : allRows) {
            System.out.println(row);
        }
    }
}
