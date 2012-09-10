@echo off
for /l %%i in (27018,1,27020) do (
start /b mongod.exe^
 --dbpath %%i^
 --logpath %%i.log^
 --port %%i^
 --replSet test^
 --rest
)
