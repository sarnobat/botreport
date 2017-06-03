cd ..
javac ReportBotsExceedingEstimates.java
 
java ReportBotsExceedingEstimates tests/emptyFile_input.txt 	| tee tests/emptyFile_output.txt
java ReportBotsExceedingEstimates tests/emptyLines_input.txt	| tee tests/emptyLines_output.txt
java ReportBotsExceedingEstimates tests/headings_input.txt		| tee tests/headings_output.txt
java ReportBotsExceedingEstimates tests/invalidLines_input.txt	| tee tests/invalidLines_output.txt