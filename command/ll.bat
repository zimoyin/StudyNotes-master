:: alias 
@echo off 
setlocal enabledelayedexpansion

:: 设置保存路径
set savePath=%~dp0

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

%savePath%ls.exe %params%
