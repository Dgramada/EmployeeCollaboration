# EmployeeCollaboration
A program that returns the two employees that have collaborated on a project for the longest period of time.
For the program to work the user needs to enter the path of the text file into the main method.

Example:
public static void main(String[] args) throws FileNotFoundException {
        longestPartners(writeArray("filePath"), "filePath");
    }

There is a dummyData.txt file that can be used to run the program.

The text file used for the program needs to be in the format:
EmpID, ProjectID, DateFrom, DateTo

Example data:
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2014-01-05
...

The format of the dates is: yyyy-MM-dd
The NULL value used instead of an exact date is equivalent to the current date.
