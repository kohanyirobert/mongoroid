@echo off
for /l %%i in (27018,1,27020) do (
start /b mongod.exe^
 --auth^
 --dbpath %%i^
 --logpath %%i.log^
 --keyFile keyFile.txt^
 --port %%i^
 --replSet test^
 --rest
)
