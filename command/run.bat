@echo off
setlocal enabledelayedexpansion

:: 检查参数数量
if "%~1"=="" (
    echo 用法: run.bat 文件名
    goto end
)

:: 获取文件名和扩展名
set "filename=%~1"
set "extension=%~x1"

:: 根据扩展名调用对应的程序
if /I "%extension%"==".py" (
    python "%filename%"
    goto end
)

if /I "%extension%"==".jar" (
    java -jar "%filename%"
    goto end
)

if /I "%extension%"==".class" (
    java "%~n1"
    goto end
)

if /I "%extension%"==".java" (
    javac "%filename%"
    if exist "%~n1.class" (
        java "%~n1"
        del "%~n1.class"
    ) else (
        echo 编译失败，未找到 %~n1.class 文件
    )
    goto end
)

if /I "%extension%"==".bat" (
    call "%filename%"
    goto end
)

if /I "%extension%"==".cmd" (
    call "%filename%"
    goto end
)

if /I "%extension%"==".lnk" (
    powershell -Command "Invoke-Item '%filename%'"
    goto end
)

:: 检查是否为文件夹，并且是否为 .idea 文件夹
if "%filename%"==".idea" (
    idea "%filename%"
    goto end
)

if /I "%extension%"==".exe" (
    "%filename%"
    goto end
)

if /I "%extension%"==".kt" (
    kotlinc "%filename%" -include-runtime -d "%~n1.jar"
    if exist "%~n1.jar" (
        java -jar "%~n1.jar"
        del "%~n1.jar"
    ) else (
        echo 编译失败，未找到 %~n1.jar 文件
    )
    goto end
)

if /I "%extension%"==".kts" (
    kotlinc -script "%filename%"
    goto end
)

if /I "%extension%"==".ps1" (
    powershell -ExecutionPolicy Bypass -File "%filename%"
    goto end
)

if /I "%extension%"==".c" (
    gcc "%filename%" -o "%~n1" || tcc "%filename%" -o "%~n1"
    if exist "%~n1.exe" (
        "%~n1.exe"
        del "%~n1.exe"
    ) else (
        echo 编译失败，未找到 %~n1.exe 文件
    )
    goto end
)

if /I "%extension%"==".go" (
    go run "%filename%"
    goto end
)

if /I "%extension%"==".js" (
    node "%filename%"
    goto end
)

if /I "%extension%"==".ts" (
    ts-node "%filename%"
    goto end
)

if /I "%extension%"==".rs" (
    rustc "%filename%"
    if exist "%~n1.exe" (
        "%~n1.exe"
        del "%~n1.exe"
    ) else (
        echo 编译失败，未找到 %~n1.exe 文件
    )
    goto end
)

:: 以下后缀使用默认程序打开
if /I "%extension%"==".doc" (
    goto run
) else if /I "%extension%"==".docx" (
    goto run
) else if /I "%extension%"==".txt" (
    goto run
) else if /I "%extension%"==".xls" (
    goto run
) else if /I "%extension%"==".xlsx" (
    goto run
) else if /I "%extension%"==".ppt" (
    goto run
) else if /I "%extension%"==".pptx" (
    goto run
) else if /I "%extension%"==".log" (
    goto run
) else if /I "%extension%"==".md" (
    goto run
) else if /I "%extension%"==".csv" (
    goto run
)

:: 检查是否为文件夹
if exist "%filename%\" (
    if exist "%filename%\.idea\" (
        idea "%filename%"
    ) else (
        start "" "%filename%"
    )
    goto end
)


:: 对于其他扩展名，询问是否使用默认应用程序打开
echo 该文件类型没有指定的运行方式。是否使用默认应用程序打开 %filename%? (y/n)
set /p choice=
if /I "%choice%"=="y" (
    :: 检查文件是否存在
    if not exist "%filename%" (
        :: 检查是否为URL/URI
        echo %filename% | findstr /r "^http[s]*://\|^ftp://\|^file://\|^mailto:" >nul
        if %errorlevel%==0 (
            echo 该文件不存在。是否使用浏览器打开该超链接 %filename%? （y/n）
            set /p choice2=
            if /I "%choice2%"=="y" (
                start %filename%
            ) else (
                echo 文件不存在: %filename%
            )
            goto end
        )
        echo 文件不存在: %filename%
        goto end
    ) else (
        start "" "%filename%"
    )
) else (
    echo 已取消操作
)

:run
start "" "%filename%"
:end
endlocal
