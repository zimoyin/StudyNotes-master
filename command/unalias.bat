@echo off
setlocal enabledelayedexpansion

:: 检查参数数量
if "%~1"=="" (
    echo 用法: unalias.bat 别名
    goto end
)

:: 获取别名
set alias=%1

:: 设置保存路径
set savePath=%~dp0

:: 构建别名文件路径
set aliasFile=%savePath%\%alias%.bat

:: 检查别名文件是否存在
if not exist "%aliasFile%" (
    echo 别名 %alias% 不存在: %savePath%
    goto end
)

:: 读取文件第一行
set /p firstLine=<"%aliasFile%"

:: 判断第一行是否为 alias 注释
if "%firstLine%"==":: alias " (
    del "%aliasFile%"
    echo 别名 %alias% 已删除
) else (
    echo %alias% 不是一个有效的别名文件
)

:end
endlocal
