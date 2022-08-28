@Echo Off

chcp 65001

for /f "delims=" %%a in ('dir /b .\data\*.sql^|jsort /n /i') do call :RunScript  %%a Catalog


pause

:RunScript

Echo Executing %1 in %2

SQLCMD -S . -U sa -P Spark-It2020 -d %2 -i .\data\%1 -f 65001

Echo Completed %1

:END