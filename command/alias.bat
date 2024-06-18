@echo off
setlocal enabledelayedexpansion

:: 设置保存路径
set savePath=%~dp0

:: 检查参数数量
if "%~2"=="" (
    if not exist "%savePath%" (
        echo 目录 %savePath% 不存在
        goto end
    )
    
    echo 别名列表:
    
    for %%f in ("%savePath%\*.bat") do (
        set aliasFile=%%f
        set aliasName=%%~nf
        
        :: 读取文件第一行
        set /p firstLine=<"%%f"
        
        :: 判断第一行是否为 alias 注释
        if "!firstLine!"==":: alias " (
            echo !aliasName!
        )
    )
    goto end
)

:: 获取别名和命令
set alias=%1
set command=%2

:: 构建别名文件路径
set aliasFile=%savePath%\%alias%.bat

:: 检查保存路径
if exist "%aliasFile%" (
    echo 该命令已经存在禁止创建
    goto end
)

:: 将命令保存到别名文件中，并添加注释
echo :: alias > "%aliasFile%"
echo @echo off >> "%aliasFile%"
echo %command% >> "%aliasFile%"

echo 别名 %alias% 已创建，指向命令: %command%

:end
endlocal
