@echo off
setlocal enabledelayedexpansion

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

rem 打印用于调试，显示构造的参数字符串
echo [DEBUG]调用7-Zip的参数: %params%

rem 调用7-Zip程序并传递所有参数
D:\application\7-Zip\7z.exe %params%

endlocal