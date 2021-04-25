package com.yordan;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        longestPartners(writeArray("C:\\Users\\jorda\\OneDrive\\Desktop\\Projects\\EmployeeCollaboration\\dummyData.txt"), "C:\\Users\\jorda\\OneDrive\\Desktop\\Projects\\EmployeeCollaboration\\dummyData.txt");
    }

    /**
     * A method that counts the lines in the txt file.
     * @param fileName the path to the file
     * @return the number of the lines in the text file
     * @throws FileNotFoundException if the file is not found through the path given as a parameter
     */
    public static int countLines(String fileName) throws FileNotFoundException {
        int count = 0;

        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                count++;
                sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Creates a double array that contains all the information for an employee on a row.
     * @param fileName the path to the file
     * @return the array containing the information from the txt file
     * @throws FileNotFoundException if the file is not found through the path given as a parameter
     */
    public static String[][] writeArray(String fileName) throws FileNotFoundException {
        String[][] array = new String[countLines(fileName)][4];

        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            String[] line;

            for (int i = 0; i < countLines(fileName); i++) {
                line = sc.nextLine().replaceAll("\\s+", "").split(",");
                for (int j = 0; j < line.length; j++) {
                    array[i][j] = line[j];
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return array;
    }

    /**
     * A method that checks if two employees have collaborated for a project at some point in the time.
     * @param array the array containing the txt information
     * @param employee1 the first employee
     * @param employee2 the second employee
     * @return true if the two employees worked together, false otherwise
     */
    public static boolean hadCollaborated(String[][] array, int employee1, int employee2) {
        if (!array[employee1][1].equals(array[employee2][1]) || array[employee1][0].equals(array[employee2][0])) {
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if (array[employee1][3].equals("NULL")) {
            array[employee1][3] = formatter.format(date);
        }
        String[] endDate1 = array[employee1][3].split("-");
        String[] startDate1 = array[employee1][2].split("-");

        if (array[employee2][3].equals("NULL")) {
            array[employee2][3] = formatter.format(date);
        }
        String[] endDate2 = array[employee2][3].split("-");
        String[] startDate2 = array[employee2][2].split("-");

        if (Integer.parseInt(endDate1[0]) - Integer.parseInt(startDate2[0]) < 0) {
            return false;
        } else if (Integer.parseInt(endDate2[0]) - Integer.parseInt(startDate1[0]) < 0) {
            return false;
        } else if (Integer.parseInt(endDate1[0]) - Integer.parseInt(startDate2[0]) == 0) {
            if (Integer.parseInt(endDate1[1]) - Integer.parseInt(startDate2[1]) < 0) {
                return false;
            } else if (Integer.parseInt(endDate1[1]) - Integer.parseInt(startDate2[2]) == 0) {
                return Integer.parseInt(endDate1[2]) - Integer.parseInt(startDate2[2]) >= 0;
            }
        } else if (Integer.parseInt(endDate2[0]) - Integer.parseInt(startDate1[0]) == 0) {
            if (Integer.parseInt(endDate2[1]) - Integer.parseInt(startDate1[1]) < 0) {
                return false;
            } else if (Integer.parseInt(endDate2[1]) - Integer.parseInt(startDate1[2]) == 0) {
                return Integer.parseInt(endDate2[2]) - Integer.parseInt(startDate1[2]) >= 0;
            }
        }

        return true;
    }

    /**
     * A method that finds the duration that two employees have worked on a project together.
     * @param array the array containing the txt file information
     * @param employee1 the first employee
     * @param employee2 the second employee
     * @return the duration that the 2 employees have worked together
     */
    public static long collaborationDuration(String[][] array, int employee1, int employee2) {
        if (!hadCollaborated(array, employee1, employee2)) {
            return 0;
        }

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 0;
        long lowerThreshold;
        long upperThreshold;


        try {
            Date startDate1 = myFormat.parse(array[employee1][2]);
            Date endDate1 = myFormat.parse(array[employee1][3]);

            Date startDate2 = myFormat.parse(array[employee2][2]);
            Date endDate2 = myFormat.parse(array[employee2][3]);

            if (startDate1.getTime() - startDate2.getTime() < 0) {
                lowerThreshold = startDate2.getTime();
            } else {
                lowerThreshold = startDate1.getTime();
            }

            if (endDate1.getTime() - endDate2.getTime() < 0) {
                upperThreshold = endDate1.getTime();
            } else {
                upperThreshold = endDate2.getTime();
            }

            diff = upperThreshold - lowerThreshold;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diff;
    }

    /**
     * A method that prints the IDs of the two partners that have collaborated for a project together the longest.
     * @param array the array containing the txt file information
     * @param fileName the path to the text file
     * @throws FileNotFoundException if the file is not found through the path given as a parameter
     */
    public static void longestPartners(String[][] array, String fileName) throws FileNotFoundException {
        long longestCollaboration = 0;
        String employee1 = "";
        String employee2 = "";

        for (int i = 0; i < countLines(fileName); i ++) {
            for (int j = 1; j < countLines(fileName); j ++) {
                if (collaborationDuration(array, i, j) > longestCollaboration) {
                    longestCollaboration = collaborationDuration(array, i , j);
                    employee1 = array[i][0];
                    employee2 = array[j][0];
                }
            }
        }
        System.out.println("The partners that have worked on a project the longest together are: "
                + "\n" + employee1
                + "\n" + employee2);
    }
}