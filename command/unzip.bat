@echo off
setlocal enabledelayedexpansion

:: 初始化变量
set zipfile=
set destdir=.
set password=
set flatten=

:: 解析参数
:parse
if "%~1"=="" goto endparse
if "%~1"=="-P" (
    set password=%2
    shift
    shift
    goto parse
)
if "%~1"=="-p" (
    set password=%2
    shift
    shift
    goto parse
)
if "%~1"=="-j" (
    set flatten=-r
    shift
    goto parse
)
if "%~1"=="-d" (
    set destdir=%2
    shift
    shift
    goto parse
)
if "%~1"=="-n" (
    set name=%2
    shift
    shift
    goto parse
)
:: 如果没有使用 -f 参数并且参数不是以 - 开头且长度不等于 2，则认为是压缩文件
if "%zipfile%"=="" if not "%~1:~0,1%"=="-" if not "%~1:~0,2%"=="--" (
    set zipfile=%1
    shift
    goto parse
)
shift
goto parse

:endparse

:: 检查是否提供了压缩文件
if "%zipfile%"=="" (
    echo 需要指定压缩文件，请使用 -f 参数
    goto end
)

:: 构建 7z 命令
set cmd="7z" x "%zipfile%" -o"%destdir%"
if not "%password%"=="" set cmd=%cmd% -p"%password%"
if not "%flatten%"=="" set cmd=%cmd% %flatten%

:: 执行 7z 命令
echo 执行: %cmd%
%cmd%

:end
endlocal
