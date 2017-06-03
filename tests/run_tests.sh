cd ..
javac ReportBotsExceedingEstimates.java
cd -
 
java ../ReportBotsExceedingEstimates ./emptyFile_input.txt 		| tee emptyFile_output.txt
java ../ReportBotsExceedingEstimates ./emptyLines_input.txt		| tee emptyLines_output.txt
java ../ReportBotsExceedingEstimates ./headings_input.txt		| tee headings_output.txt
java ../ReportBotsExceedingEstimates ./invalidLines_input.txt	| tee invalidLines_output.txt