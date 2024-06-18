:: alias 
@echo off 
rem 存储参数的变量初始化
set "params="

rem 遍历所有传入的参数
for %%i in (%*) do (
    rem 将当前参数追加到params变量后，用空格分隔
    if defined params (
        set "params=!params! %%i"
    ) else (
        set "params=%%i"
    )
)
code %params%
