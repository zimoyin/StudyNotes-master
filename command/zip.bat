@echo off
setlocal enabledelayedexpansion

:: 初始化变量
set zipfile=
set sources=
set recursive=
set silent=
set delete=
set update=
set freshen=
set move=
set encrypt=
set comment=

:: 解析参数
:parse
if "%~1"=="" goto endparse
if "%~1"=="-r" (
    set recursive=-r
    shift
    goto parse
)
if "%~1"=="-q" (
    set silent=-bd
    shift
    goto parse
)
if "%~1"=="-d" (
    set delete=1
    shift
    goto parse
)
if "%~1"=="-u" (
    set update=-u
    shift
    goto parse
)
if "%~1"=="-f" (
    set freshen=1
    shift
    goto parse
)
if "%~1"=="-m" (
    set move=-sdel
    shift
    goto parse
)
if "%~1"=="-e" (
    set encrypt=1
    shift
    goto parse
)
if "%~1"=="-z" (
    set comment=%2
    shift
    shift
    goto parse
)
:: 如果没有使用 -r 等参数并且参数不是以 - 开头且长度不等于 2，则认为是压缩包名或源文件
if "%zipfile%"=="" if not "%~1:~0,1%"=="-" if not "%~1:~0,2%"=="--" (
    set zipfile=%1
    shift
    goto parse
)
if "%sources%"=="" if not "%~1:~0,1%"=="-" if not "%~1:~0,2%"=="--" (
    set sources=%1
    shift
    goto parse
)
shift
goto parse

:endparse

:: 如果没有提供第二个未识别的参数，将第一个未识别的参数视为源文件或目录
if "%sources%"=="" (
    set sources=%zipfile%
    set zipfile=%sources%.zip
)

:: 检查是否提供了压缩包名和源文件
if "%sources%"=="" (
    echo 需要指定源文件或源目录，请提供第一个非参数项
    goto end
)

:: 构建 7z 命令
set cmd="7z" a "%zipfile%" "%sources%" %recursive% %silent% %move%

:: 如果是删除模式，使用d命令而不是a命令
if defined delete (
    set cmd="7z" d "%zipfile%" "%sources%"
)

:: 如果是更新模式，使用u命令而不是a命令
if defined update (
    set cmd="7z" u "%zipfile%" "%sources%"
)

:: 如果是freshen模式，只更新存在的文件
if defined freshen (
    set cmd="7z" u "%zipfile%" "%sources%" -u
)

:: 如果是加密模式，添加 -p 选项
if defined encrypt (
    set /p password=请输入密码:
    set cmd=%cmd% -p"%password%"
)

:: 如果有注释，添加 -z 选项
if not "%comment%"=="" (
    set cmd=%cmd% -z"%comment%"
)

:: 执行 7z 命令
echo 执行: %cmd%
%cmd%

:end
endlocal
